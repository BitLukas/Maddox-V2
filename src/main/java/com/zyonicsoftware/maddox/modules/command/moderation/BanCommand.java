/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.modules.command.moderation;

import com.zyonicsoftware.maddox.core.engine.handling.command.Command;
import com.zyonicsoftware.maddox.core.engine.handling.command.CommandEvent;
import com.zyonicsoftware.maddox.core.engine.helpbuilder.CommandHelpViewPermission;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;
import com.zyonicsoftware.maddox.core.main.Maddox;
import de.daschi.javalanguageapi.api.LanguageAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.exceptions.RateLimitedException;

public class BanCommand extends Command {

    private final Maddox maddox;

    public BanCommand(final Maddox maddox) {
        this.maddox = maddox;
        this.setName("ban");
        this.setDescription("Ban-Desc");
        this.setSyntax("Ban-Syntax");
        this.setCategory("Moderation-Category");
        this.setGetValuesFromLanguageYAML(true);
        this.setAllowExecutionOnMessageEdit(true);
        this.setCommandHelpViewPermission(CommandHelpViewPermission.MEMBER_BAN);
        this.setToggleable(true);
    }

    @Override
    protected void execute(final CommandEvent event, final MaddoxMember sender, final MaddoxGuild server) {
        try {
            if (sender.hasPermission(Permission.BAN_MEMBERS)) {
                if (!event.getMentions().isEmpty()) {
                    if (event.getArguments().size() > 1) {
                        final String memberID = event.getMentions().get(0).getID();
                        final String memberName = event.getMentions().get(0).getUsername();
                        try {
                            event.getMentions().get(0).ban(0, event.getArgumentsAsString().replace("<@!" + memberID + ">" + " ", "")).complete(true);

                            if (event.getGuild().retrieveMemberById(memberID).complete(true) == null) {
                                event.reply(
                                        new EmbedBuilder()
                                                .addField("Ban", LanguageAPI.getValue("Ban-With-Reason").replace("<USER>", memberName).replace("<REASON>", event.getArgumentsAsString().replace("<@!" + memberID + ">" + " ", "")), false)
                                                .setColor(this.maddox.getDefaultColor())
                                                .build()
                                );
                            } else {
                                event.reply(LanguageAPI.getValue("Ban-SWW", server.getLanguage()));
                            }
                        } catch (final RateLimitedException ignored) {
                        }
                    } else {
                        final String memberName = event.getMentions().get(0).getUsername();
                        event.getMentions().get(0).ban(0).queue();
                        event.reply(
                                new EmbedBuilder()
                                        .addField("Ban", LanguageAPI.getValue("Ban-Without-Reason").replace("<USER>", memberName), false)
                                        .setColor(this.maddox.getDefaultColor())
                                        .build()
                        );
                    }
                } else {
                    event.reply(LanguageAPI.getValue("Ban-NoPersonDefined", server.getLanguage()).replace("<PREFIX>", event.getPrefix()));
                }
            }
        } catch (final Exception ignored) {
            event.reply(LanguageAPI.getValue("SWW", server.getLanguage()));
        }
    }
}
