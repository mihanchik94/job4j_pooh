package pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService  implements Service {
    private final ConcurrentHashMap<String,
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> queue
            = new ConcurrentHashMap<>();


    @Override
    public Resp process(Req req) {
        Resp rsl = new Resp(req.getParam(), "201");
        String reqType = req.httpRequestType();
        switch (reqType) {
            case "POST" -> queue.get(req.getSourceName()).values().forEach(v -> v.add(req.getParam()));
            case "GET" -> {
                queue.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
                ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map =
                        queue.get(req.getSourceName());
                map.putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
                String s = map.get(req.getParam()).poll();
                if (s != null) {
                    rsl = new Resp(s, "200");
                }
            }
            default -> new Resp("", "501");
        }
        return rsl;
    }
}
