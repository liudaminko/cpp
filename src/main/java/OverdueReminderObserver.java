import java.util.List;
import java.util.Map;

class OverdueReminderObserver implements Observer {
    private Library library;

    public OverdueReminderObserver(Library library) {
        this.library = library;
    }

    @Override
    public void notify(List<User> users) {
        for (User user : users) {
            Map<String, Long> readerOverdueInfo = getOverdueInfoForReader(user);

            if (readerOverdueInfo != null && !readerOverdueInfo.isEmpty()) {
                StringBuilder notification = new StringBuilder("overdue reminder notification. overdue books:\n");
                readerOverdueInfo.forEach((bookTitle, daysOverdue) ->
                        notification.append("   • ").append(bookTitle)
                                .append(" overdue by ").append(daysOverdue).append(" days\n"));

                user.addNotification(notification.toString());
            }
        }
    }

    private Map<String, Long> getOverdueInfoForReader(User user) {
        return library.getOverdueInfoForReader(user);
    }
}