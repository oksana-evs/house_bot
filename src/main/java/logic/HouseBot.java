package logic;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HouseBot extends TelegramLongPollingBot {
    private static final List<Integer> allowedUserIds = List.of(104051418, 34173811);

    @Override
    public void onUpdateReceived(Update update) {
        Message userMessage = update.getMessage();
        if (userMessage != null && userMessage.hasText()) {
            if (!allowedUserIds.contains(userMessage.getFrom().getId())) {
                sendMsg(userMessage, "Sorry, this is a private bot");
                return;
            }
            final String text = userMessage.getText();
            switch (text) {
                case "/start":
                    sendMsg(userMessage, "Type /list to see available things to do");
                    break;
                case "/list":
                    sendMsg(userMessage, getThingsToDo());
                    break;
                case "/vacuum":
                    sendMsg(userMessage, "You are vacuum cleaning!");
                    HouseCleaning.VACUUMING.setDone(true);
                    break;
                case "/sink":
                    sendMsg(userMessage, "You are cleaning the sinks (kitchen + bathroom)!");
                    HouseCleaning.SINK_WASHING.setDone(true);
                    break;
                case "/hob":
                    sendMsg(userMessage, "You are cleaning the hob!");
                    HouseCleaning.HOB.setDone(true);
                    break;
                case "/dust":
                    sendMsg(userMessage, "You are dusting off!");
                    HouseCleaning.DUST_OFF.setDone(true);
                    break;
                case "/wc":
                    sendMsg(userMessage, "You are cleaning the toilet!");
                    HouseCleaning.WC.setDone(true);
                    break;
                case "/reset":
                    HouseCleaning.reset();
                    sendMsg(userMessage, "Reset");
                    break;
                default:
                    sendMsg(userMessage,"Unknown command!");

            }
        }
    }

    @Override
    public String getBotUsername() {
        return "houseworkbot";
    }

    @Override
    public String getBotToken() {
        return "1341098322:AAFOFWvffBTeTuvl3eDWSdGFJfF0XcVUCEQ";
    }

    private void sendMsg(Message message, String text) {
        SendMessage response = new SendMessage()
                .setChatId(message.getChatId())
                .setText(text);
        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getThingsToDo() {
        String thingsToDo = Stream.of(HouseCleaning.values())
                .filter(t -> !t.isDone())
                .map(HouseCleaning::getName)
                .collect(Collectors.joining("\n"));
        return thingsToDo.isEmpty() ? "Nothing has to be done" : thingsToDo;
    }

}
