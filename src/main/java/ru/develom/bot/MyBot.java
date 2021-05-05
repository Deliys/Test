package ru.develom.bot;

import api.longpoll.bots.BotsLongPoll;
import api.longpoll.bots.LongPollBot;
import api.longpoll.bots.exceptions.BotsLongPollException;
import api.longpoll.bots.exceptions.BotsLongPollHttpException;
import api.longpoll.bots.methods.messages.MessagesSend;
import api.longpoll.bots.model.events.boards.BoardPostEvent;
import api.longpoll.bots.model.events.messages.MessageNewEvent;
import api.longpoll.bots.model.objects.additional.Button;
import api.longpoll.bots.model.objects.additional.Keyboard;
import api.longpoll.bots.model.objects.basic.Message;
import api.longpoll.bots.model.objects.basic.WallPost;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyBot extends LongPollBot {
    @Override
    public void onMessageNew(MessageNewEvent messageNewEvent) {
        Message message = messageNewEvent.getMessage();


        Date date = new Date();

        if (message.getText().toUpperCase().equals("ВРЕМЯ")) {
            try {
                new MessagesSend(this)
                        .setPeerId(message.getPeerId())
                        .setMessage("Время на сервере: "+date.toString())
                        .execute();
            } catch (BotsLongPollHttpException | BotsLongPollException e) {
                System.out.println("error");
            }
        }
        if (message.getText().toUpperCase().equals("ИНФО")) {
            try {
                new MessagesSend(this)
                        .setPeerId(message.getPeerId())
                        .setMessage("Вся инфа из сообщения: \n"+"Время отправки сообщения в UNIX формате: "
                                +message.getDate().toString()+"\n"+"ID отправителя: "+message.getFromId().toString()+"\n"
                                +"Сообщений в беседе: "+message.getConversationMessageId().toString()+"\n")
                        .execute();
            } catch (BotsLongPollHttpException | BotsLongPollException e) {
                System.out.println("error");
            }
        }
        if (message.hasGeo()) {
            try {
                new MessagesSend(this)
                        .setPeerId(message.getPeerId())
                        .setMessage("Твои координаты: "+message.getGeo().getCoordinates().getLatitude().toString()+" "+message.getGeo().getCoordinates().getLongitude().toString())
                        .execute();
            } catch (BotsLongPollHttpException | BotsLongPollException e) {
                System.out.println("error");
            }
        }
        else if (message.hasAttachments()) {
            try {
                new MessagesSend(this)
                        .setPeerId(message.getPeerId())
                        .setMessage("В сообщении есть вложение!")
                        .execute();
            } catch (BotsLongPollHttpException | BotsLongPollException e) {
                System.out.println("error");
            }
        }
        else if (message.getText().toUpperCase().equals("КАРТИНКА")) {
            try {
                new MessagesSend(this)
                        .setPeerId(message.getPeerId())
                        .attachPhoto(new File("F:\\бот в беседе\\фреско.jpg"))
                        .execute();
            } catch (BotsLongPollHttpException | BotsLongPollException e) {
                System.out.println("error");
            }
        }
    }

    @Override
    public String getAccessToken() {
        return "641ba260750fda10699f228ebe88a3083aa22501c0c3230d142d93e1c4910701297a587ee24110f4d010d";
    }

    @Override
    public int getGroupId() {
        return 159532685;
    }


    public static void main(String[] args) throws BotsLongPollHttpException, BotsLongPollException {
        new BotsLongPoll(new MyBot()).run();
    }
}
