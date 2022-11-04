package master.appcondidature.payload.response;

public class MessageResponse {
    private String message;
    private  long message1;

    public MessageResponse(String message) {
        this.message = message;
    }

    public void MessageResponseLong(Long message) {
        this.message1 = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
