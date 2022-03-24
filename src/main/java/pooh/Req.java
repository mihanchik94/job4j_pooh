package pooh;

public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] array = content.split(System.lineSeparator());
        String[] rsl = array[0].split("/");
        String request;
        String pooh;
        String source;
        String param;
        if (array.length > 4) {
            request = rsl[0].substring(0, rsl[0].indexOf(" "));
            pooh = rsl[1];
            source = rsl[2].substring(0, rsl[2].indexOf(" "));
            param = array[array.length - 1];
        } else {
            request = rsl[0].substring(0, rsl[0].indexOf(" "));
            pooh = rsl[1];
            source = rsl.length > 4 ? rsl[2] : rsl[2].substring(0, rsl[2].indexOf(" "));
            param = rsl.length > 4 ? rsl[3].substring(0, rsl[3].indexOf(" "))
                    : "";
        }
        return new Req(request, pooh, source, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
