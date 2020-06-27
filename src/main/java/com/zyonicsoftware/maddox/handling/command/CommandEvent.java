/*
 * 2020. Zyonic Software - 2020. - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.handling.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandEvent {

    private Command command;
    private GuildMessageReceivedEvent receivedEvent;
    private GuildMessageUpdateEvent updateEvent;
    private String prefix;


    //Constructors Differenciating between Events, Command Event also gets Triggered on MessageEdit, if enabled in Command
    public CommandEvent(Command command, GuildMessageReceivedEvent receivedEvent, String prefix) {
        this.command = command;
        this.receivedEvent = receivedEvent;
        this.prefix = prefix;
    }

    public CommandEvent(Command command, GuildMessageUpdateEvent updateEvent, String prefix){
        this.command = command;
        this.updateEvent = updateEvent;
        this.prefix = prefix;
    }


    public Command getCommand() {
        return command;
    }

    public TextChannel getChannel() {
        if (receivedEvent != null) {
            return receivedEvent.getChannel();
        } else {
            return updateEvent.getChannel();
        }
    }

    public Category getCategory() {
        return this.getChannel().getParent();
    }

    public User getUser() {
        if (receivedEvent != null) {
            return receivedEvent.getMember().getUser();
        } else {
            return updateEvent.getMember().getUser();
        }
    }

    public Member getMember() {
        if (receivedEvent != null) {
            return receivedEvent.getMember();
        } else {
            return updateEvent.getMember();
        }
    }

    public User getAuthor() {
        if (receivedEvent != null) {
            return receivedEvent.getAuthor();
        } else {
            return updateEvent.getAuthor();
        }
    }

    public Guild getGuild(){
        if(receivedEvent != null){
            return receivedEvent.getGuild();
        } else {
            return updateEvent.getGuild();
        }
    }

    public JDA getJDA() {
        if (receivedEvent != null) {
            return receivedEvent.getJDA();
        } else {
            return updateEvent.getJDA();
        }
    }

    public String getMessageID() {
        if (receivedEvent != null) {
            return receivedEvent.getMessageId();
        } else {
            return updateEvent.getMessageId();
        }
    }

    public boolean isWebhookMessage() {
        if (receivedEvent != null) {
            return receivedEvent.isWebhookMessage();
        } else {
            return false;
        }
    }

    public Message getMessage() {
        if (receivedEvent != null) {
            return receivedEvent.getMessage();
        } else {
            return updateEvent.getMessage();
        }
    }

    public String getMessageText() {
        if (receivedEvent != null) {
            return receivedEvent.getMessage().getContentRaw();
        } else {
            return updateEvent.getMessage().getContentRaw();
        }
    }

    public GuildMessageReceivedEvent getMessageReceivedEvent() {
        if (receivedEvent != null) {
            return receivedEvent;
        } else {
            return null;
        }
    }

    public GuildMessageUpdateEvent getMessageUpdateEvent() {
        if (updateEvent != null) {
            return updateEvent;
        } else {
            return null;
        }
    }


    public String getPrefix() {
        return prefix;
    }

    public boolean equals(CommandEvent commandEvent) {
        if (commandEvent.getMessageReceivedEvent() != null) {
            return receivedEvent.equals(commandEvent.getMessageReceivedEvent());
        } else {
            return updateEvent.equals(commandEvent.getMessageUpdateEvent());
        }
    }

    public String getArgumentsAsString() {
        if (!this.getArguments().isEmpty()) {
            if (receivedEvent != null) {
                return receivedEvent.getMessage().getContentRaw().substring(command.getName().length() + prefix.length() + 1);
            } else {
                return updateEvent.getMessage().getContentRaw().substring(command.getName().length() + prefix.length() + 1);
            }
        } else {
            return "";
        }
    }

    public List<String> getArguments() {
        String arguments;
        if (receivedEvent != null) {
            arguments = receivedEvent.getMessage().getContentRaw().substring(command.getName().length() + prefix.length());
            if (receivedEvent.getMessage().getContentRaw().startsWith(prefix + " ")) {
                arguments = receivedEvent.getMessage().getContentRaw().substring(command.getName().length() + prefix.length() + 1);
            }
        } else {
            arguments = updateEvent.getMessage().getContentRaw().substring(command.getName().length() + prefix.length());
            if (updateEvent.getMessage().getContentRaw().startsWith(prefix + " ")) {
                arguments = updateEvent.getMessage().getContentRaw().substring(command.getName().length() + prefix.length() + 1);
            }
        }
        if (arguments.length() > 0) {
            arguments = arguments.substring(1);
            String[] args = arguments.split(" ");
            return Arrays.asList(args);
        } else {
            return new ArrayList<String>();
        }
    }

    public String getExecutorAvatar() {
        if (receivedEvent != null) {
            return "https://cdn.discordapp.com/avatars/" + receivedEvent.getMember().getId() + "/" + receivedEvent.getMember().getUser().getAvatarId() + ".png?size=2048";
        } else {
            return "https://cdn.discordapp.com/avatars/" + updateEvent.getMember().getId() + "/" + updateEvent.getMember().getUser().getAvatarId() + ".png?size=2048";
        }
    }

    public List<Member> getMentions() {
        List<String> memberIDs = new ArrayList<>();
        List<Member> members = new ArrayList<>();

        this.getArguments().forEach(argument -> {
            if (argument.startsWith("<@") && argument.endsWith(">")) {
                argument = argument.replace("<@", "").replace(">", "").replace("!", "");
                memberIDs.add(argument);
            }
        });

        memberIDs.forEach(userID -> {
            try {
                members.add(this.getGuild().getMemberById(userID));
            } catch (Exception e) {

            }
        });

        return members;
    }

    public List<TextChannel> getTextChannelMentions() {
        List<String> textChannelIDs = new ArrayList<>();
        List<TextChannel> textChannels = new ArrayList<>();

        this.getArguments().forEach(argument -> {
            if (argument.startsWith("<@#") && argument.endsWith(">")) {
                argument = argument.replace("<@#", "").replace(">", "").replace("!", "");
                textChannelIDs.add(argument);
            }
        });

        textChannelIDs.forEach(channelID -> {
            try {
                textChannels.add(this.getGuild().getTextChannelById(channelID));
            } catch (Exception e) {

            }
        });

        return textChannels;
    }

    public List<Role> getRoleMentions(){
        List<String> roleIDs = new ArrayList<>();
        List<Role> roles = new ArrayList<>();

        this.getArguments().forEach(argument -> {
            if(argument.startsWith("<@&") && argument.endsWith(">")){
                argument = argument.replace("<@&", "").replace(">", "").replace("!", "");
                roleIDs.add(argument);
            }
        });

        roleIDs.forEach(roleid -> {
            try {
                roles.add(this.getGuild().getRoleById(roleid));
            } catch (Exception e){}
        });

        return roles;
    }


    //New Methods

    public void reply(String pMessage) {
        if (receivedEvent != null) {
            receivedEvent.getChannel().sendMessage(pMessage).queue();
        } else {
            updateEvent.getChannel().sendMessage(pMessage).queue();
        }
    }

    public void reply(MessageEmbed messageEmbed) {
        if (receivedEvent != null) {
            receivedEvent.getChannel().sendMessage(messageEmbed).queue();
        } else {
            updateEvent.getChannel().sendMessage(messageEmbed).queue();
        }
    }

    public void deleteEventMessage() {
        if (receivedEvent != null) {
            receivedEvent.getMessage().delete().queue();
        } else {
            updateEvent.getMessage().delete().queue();
        }
    }

}
