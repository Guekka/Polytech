package test3.mailsystem;

public class MailItemWithSubject extends MailItem {
    private final String subject;

    public MailItemWithSubject(String from, String to, String subject,String message) {
        super(from, to, message);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        // from='Wilma', to='Fred', message='Did you remember to pick up the mammoth for supper?', subject='supper'
        return "from='" + getFrom() + "', to='" + getTo() + "', message='" + getMessage() + "', subject='" + subject + "'";
    }
}
