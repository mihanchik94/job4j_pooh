package pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String,
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> queue
            = new ConcurrentHashMap<>();


    @Override
    public Resp process(Req req) {
        Resp rsl = new Resp(req.getParam(), "201");
        String reqType = req.httpRequestType();
        switch (reqType) {
            case "POST" -> {
                ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> topic = queue.get(req.getSourceName());
                if (topic != null) {
                    topic.values().forEach(v -> v.add(req.getParam()));
                }
            }
            case "GET" -> {
                queue.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
                queue.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
                String s = queue.get(req.getSourceName()).get(req.getParam()).poll();
                rsl = (s == null ? new Resp("", "204") : new Resp(s, "200"));
            }
            default -> new Resp("", "501");
        }
        return rsl;
    }
}