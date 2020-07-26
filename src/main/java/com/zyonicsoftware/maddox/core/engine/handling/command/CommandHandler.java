/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be
 * used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.handling.command;

import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;
import com.zyonicsoftware.maddox.core.main.Maddox;
import java.util.HashMap;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;

public class CommandHandler {

  private final Maddox maddox;
  private final HashMap<String, Command> commands;
  private final HashMap<String, Command> specificPrefixCommands;

  public CommandHandler(final Maddox maddox) {
    this.maddox = maddox;

    this.commands = new HashMap<>();
    this.specificPrefixCommands = new HashMap<>();
  }

  public void registerCommand(final Command command) {
    if (command.getSpecificPrefix() == null) {
      this.commands.put(command.getName(), command);
    } else {
      this.specificPrefixCommands.put(
          command.getSpecificPrefix() + command.getName(), command);
    }
  }

  public void registerCommands(final Command... commands) {
    for (int i = 0; i < commands.length; i++) {
      final Command command = commands[i];
      if (command.getSpecificPrefix() == null) {
        this.commands.put(command.getName(), command);
      } else {
        this.specificPrefixCommands.put(
            command.getSpecificPrefix() + command.getName(), command);
      }
    }
  }

  public void handle(final GuildMessageReceivedEvent event, final String prefix,
                     final String messageContent) {

    if (messageContent.startsWith(prefix + " ")) {

      String[] seperatedStrings =
          messageContent.substring(prefix.length() + 1).split(" ");

      if (seperatedStrings.length > 0) {

        final Command selectedCommand =
            this.commands.get(seperatedStrings[0].toLowerCase());

        if (selectedCommand != null) {
          if (this.maddox.isMySQLConnected()) {
            if (this.maddox.areCommandsToggleable()) {
              if (selectedCommand.isToggleable()) {
                if (!this.maddox.getCommandToggleManager()
                         .getCommandsForToggling(event.getGuild())
                         .contains(selectedCommand.getName())) {
                  return;
                }
              }
            }
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event, prefix),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix,
                                this.maddox.getCacheManager()));
            return;
          } else {
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event, prefix),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix));
            return;
          }
        }
      }

      seperatedStrings = messageContent.split(" ");

      if (seperatedStrings.length > 0) {

        final Command selectedCommand = this.specificPrefixCommands.get(
            seperatedStrings[0].toLowerCase().substring(1));
        if (selectedCommand != null) {
          if (this.maddox.isMySQLConnected()) {
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event,
                                 selectedCommand.getSpecificPrefix()),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix,
                                this.maddox.getCacheManager()));
            return;
          } else {
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event,
                                 selectedCommand.getSpecificPrefix()),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix));
            return;
          }
        }
      }

    } else if (messageContent.startsWith(prefix)) {

      String[] seperatedStrings =
          messageContent.substring(prefix.length()).split(" ");

      if (seperatedStrings.length > 0) {

        final Command selectedCommand =
            this.commands.get(seperatedStrings[0].toLowerCase());

        if (selectedCommand != null) {
          if (this.maddox.isMySQLConnected()) {
            if (this.maddox.areCommandsToggleable()) {
              if (selectedCommand.isToggleable()) {
                if (!this.maddox.getCommandToggleManager()
                         .getCommandsForToggling(event.getGuild())
                         .contains(selectedCommand.getName())) {
                  return;
                }
              }
            }
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event, prefix),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix,
                                this.maddox.getCacheManager()));
            return;
          } else {
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event, prefix),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix));
            return;
          }
        }
      }

      seperatedStrings = messageContent.split(" ");

      if (seperatedStrings.length > 0) {

        final Command selectedCommand =
            this.specificPrefixCommands.get(seperatedStrings[0].toLowerCase());
        if (selectedCommand != null) {
          if (this.maddox.isMySQLConnected()) {
            if (this.maddox.areCommandsToggleable()) {
              if (selectedCommand.isToggleable()) {
                if (!this.maddox.getCommandToggleManager()
                         .getCommandsForToggling(event.getGuild())
                         .contains(selectedCommand.getName())) {
                  return;
                }
              }
            }
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event,
                                 selectedCommand.getSpecificPrefix()),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix,
                                this.maddox.getCacheManager()));
            return;
          } else {
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event,
                                 selectedCommand.getSpecificPrefix()),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix));
            return;
          }
        }
      }
    } else {
      final String[] seperatedStrings = messageContent.split(" ");

      if (seperatedStrings.length > 0) {

        final Command selectedCommand =
            this.specificPrefixCommands.get(seperatedStrings[0].toLowerCase());
        if (selectedCommand != null) {
          if (this.maddox.isMySQLConnected()) {
            if (this.maddox.areCommandsToggleable()) {
              if (selectedCommand.isToggleable()) {
                if (!this.maddox.getCommandToggleManager()
                         .getCommandsForToggling(event.getGuild())
                         .contains(selectedCommand.getName())) {
                  return;
                }
              }
            }
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event,
                                 selectedCommand.getSpecificPrefix()),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix,
                                this.maddox.getCacheManager()));
            return;
          } else {
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event,
                                 selectedCommand.getSpecificPrefix()),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix));
            return;
          }
        }
      }
    }
  }

  public void handle(final GuildMessageUpdateEvent event, final String prefix,
                     final String messageContent) {

    if (messageContent.startsWith(prefix + " ")) {

      String[] seperatedStrings =
          messageContent.substring(prefix.length() + 1).split(" ");

      if (seperatedStrings.length > 0) {

        final Command selectedCommand =
            this.commands.get(seperatedStrings[0].toLowerCase());

        if (selectedCommand != null) {
          if (this.maddox.isMySQLConnected()) {
            if (this.maddox.areCommandsToggleable()) {
              if (selectedCommand.isToggleable()) {
                if (!this.maddox.getCommandToggleManager()
                         .getCommandsForToggling(event.getGuild())
                         .contains(selectedCommand.getName())) {
                  return;
                }
              }
            }
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event, prefix),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix,
                                this.maddox.getCacheManager()));
            return;
          } else {
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event, prefix),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix));
            return;
          }
        }
      }

      seperatedStrings = messageContent.split(" ");

      if (seperatedStrings.length > 0) {

        final Command selectedCommand =
            this.specificPrefixCommands.get(seperatedStrings[0].toLowerCase());
        if (selectedCommand != null) {
          if (this.maddox.isMySQLConnected()) {
            if (this.maddox.areCommandsToggleable()) {
              if (selectedCommand.isToggleable()) {
                if (!this.maddox.getCommandToggleManager()
                         .getCommandsForToggling(event.getGuild())
                         .contains(selectedCommand.getName())) {
                  return;
                }
              }
            }
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event,
                                 selectedCommand.getSpecificPrefix()),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix,
                                this.maddox.getCacheManager()));
            return;
          } else {
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event,
                                 selectedCommand.getSpecificPrefix()),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix));
            return;
          }
        }
      }
    } else if (messageContent.startsWith(prefix)) {

      String[] seperatedStrings =
          messageContent.substring(prefix.length()).split(" ");

      if (seperatedStrings.length > 0) {

        final Command selectedCommand =
            this.commands.get(seperatedStrings[0].toLowerCase());

        if (selectedCommand != null) {
          if (this.maddox.isMySQLConnected()) {
            if (this.maddox.areCommandsToggleable()) {
              if (selectedCommand.isToggleable()) {
                if (!this.maddox.getCommandToggleManager()
                         .getCommandsForToggling(event.getGuild())
                         .contains(selectedCommand.getName())) {
                  return;
                }
              }
            }
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event, prefix),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix,
                                this.maddox.getCacheManager()));
            return;
          } else {
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event, prefix),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix));
            return;
          }
        }
      }

      seperatedStrings = messageContent.split(" ");

      if (seperatedStrings.length > 0) {

        final Command selectedCommand =
            this.specificPrefixCommands.get(seperatedStrings[0].toLowerCase());
        if (selectedCommand != null) {
          if (this.maddox.isMySQLConnected()) {
            if (this.maddox.areCommandsToggleable()) {
              if (selectedCommand.isToggleable()) {
                if (!this.maddox.getCommandToggleManager()
                         .getCommandsForToggling(event.getGuild())
                         .contains(selectedCommand.getName())) {
                  return;
                }
              }
            }
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event,
                                 selectedCommand.getSpecificPrefix()),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix,
                                this.maddox.getCacheManager()));
            return;
          } else {
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event,
                                 selectedCommand.getSpecificPrefix()),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix));
            return;
          }
        }
      }
    } else {
      final String[] seperatedStrings = messageContent.split(" ");

      if (seperatedStrings.length > 0) {

        final Command selectedCommand =
            this.specificPrefixCommands.get(seperatedStrings[0].toLowerCase());
        if (selectedCommand != null) {
          if (this.maddox.isMySQLConnected()) {
            if (this.maddox.areCommandsToggleable()) {
              if (selectedCommand.isToggleable()) {
                if (!this.maddox.getCommandToggleManager()
                         .getCommandsForToggling(event.getGuild())
                         .contains(selectedCommand.getName())) {
                  return;
                }
              }
            }
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event,
                                 selectedCommand.getSpecificPrefix()),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix,
                                this.maddox.getCacheManager()));
            return;
          } else {
            selectedCommand.execute(
                new CommandEvent(selectedCommand, event,
                                 selectedCommand.getSpecificPrefix()),
                new MaddoxMember(event.getMember()),
                new MaddoxGuild(event.getGuild(), prefix));
            return;
          }
        }
      }
    }
  }

  public HashMap<String, Command> getCommands() { return this.commands; }

  public HashMap<String, Command> getSpecificPrefixCommands() {
    return this.specificPrefixCommands;
  }
}
