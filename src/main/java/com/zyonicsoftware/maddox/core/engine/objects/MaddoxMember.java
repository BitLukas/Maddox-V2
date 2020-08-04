/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.objects;

import com.zyonicsoftware.maddox.core.engine.helpbuilder.CommandHelpViewPermission;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

import javax.annotation.Nonnull;
import java.awt.*;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

public class MaddoxMember {

    private final Member member;

    public MaddoxMember(final Member member) {
        this.member = member;
    }

    public Member getMember() {
        return this.member;
    }

    public User getUser() {
        return this.member.getUser();
    }

    public String getNickame() {
        return this.member.getNickname();
    }

    public String getUsername() {
        return this.member.getEffectiveName();
    }

    public AuditableRestAction<Void> ban(final int deletionDays) {
        return this.member.ban(deletionDays);
    }

    public AuditableRestAction<Void> ban(final int deletionDays, @Nonnull final String reason) {
        return this.member.ban(deletionDays, reason);
    }

    public boolean canInteract(final Role role) {
        return this.member.canInteract(role);
    }

    public boolean canInteract(final Emote emote) {
        return this.member.canInteract(emote);
    }

    public boolean canInteract(final Member member) {
        return this.member.canInteract(member);
    }

    public boolean canInteract(final MaddoxMember maddoxMember) {
        return this.member.canInteract(maddoxMember.getMember());
    }

    public EnumSet<ClientType> getActiveClients() {
        return this.member.getActiveClients();
    }

    public List<Activity> getActivities() {
        return this.member.getActivities();
    }

    public Color getColor() {
        return this.member.getColor();
    }

    public int getColorRAW() {
        return this.member.getColorRaw();
    }

    public TextChannel getDefaultChannel() {
        return this.member.getDefaultChannel();
    }

    public OnlineStatus getOnlineStatus() {
        return this.member.getOnlineStatus();
    }

    public OnlineStatus getOnlineStatus(@Nonnull final ClientType clientType) {
        return this.member.getOnlineStatus(clientType);
    }

    public Guild getCurrentGuild() {
        return this.member.getGuild();
    }

    public JDA getCurrentJDA() {
        return this.member.getJDA();
    }

    public List<Role> getRoles() {
        return this.member.getRoles();
    }

    public OffsetDateTime getTimeJoined() {
        return this.member.getTimeJoined();
    }

    public OffsetDateTime getTimeBoosted() {
        return this.member.getTimeBoosted();
    }

    public boolean hasTimeJoinedValue() {
        return this.member.hasTimeJoined();
    }

    public boolean isGuildOwner() {
        return this.member.isOwner();
    }

    public AuditableRestAction<Void> kick() {
        return this.member.kick();
    }

    public AuditableRestAction<Void> kick(@Nonnull final String reason) {
        return this.member.kick(reason);
    }

    public AuditableRestAction<Void> modifyNickname(@Nonnull final String newNickname) {
        return this.member.modifyNickname(newNickname);
    }

    public String getAsMention() {
        return this.member.getAsMention();
    }

    public EnumSet<Permission> getPermissions() {
        return this.member.getPermissions();
    }

    public EnumSet<Permission> getPermissions(final GuildChannel guildChannel) {
        return this.member.getPermissions(guildChannel);
    }

    public EnumSet<Permission> getPermissionsExplicit() {
        return this.member.getPermissionsExplicit();
    }

    public EnumSet<Permission> getPermissionExplicit(final GuildChannel guildChannel) {
        return this.member.getPermissionsExplicit(guildChannel);
    }

    public boolean hasPermission(@Nonnull final Permission... permissions) {
        return this.member.hasPermission(permissions);
    }

    public boolean hasPermission(@Nonnull final Collection<Permission> permissions) {
        return this.member.hasPermission(permissions);
    }

    public boolean hasPermission(@Nonnull final GuildChannel guildChannel, @Nonnull final Permission... permissions) {
        return this.member.hasPermission(guildChannel, permissions);
    }

    public String getID() {
        return this.member.getId();
    }

    //CustomMethods

    public AuditableRestAction<Void> addRole(final Role role) {
        return this.member.getGuild().addRoleToMember(this.member, role);
    }

    public AuditableRestAction<Void> removeRole(final Role role) {
        return this.member.getGuild().removeRoleFromMember(this.member, role);
    }

    public PrivateChannel openPrivateChannel() {
        return this.member.getUser().openPrivateChannel().complete();
    }

    public CommandHelpViewPermission getCommandHelpViewPermission() {

        if (this.member.getPermissions().contains(Permission.ADMINISTRATOR)) {
            return CommandHelpViewPermission.ADMINISTRATOR;
        } else if (this.member.getPermissions().contains(Permission.MANAGE_SERVER)) {
            return CommandHelpViewPermission.MANAGE_SERVER;
        } else if (this.member.getPermissions().contains(Permission.MANAGE_ROLES)) {
            return CommandHelpViewPermission.MANAGE_ROLE;
        } else if (this.member.getPermissions().contains(Permission.KICK_MEMBERS)) {
            return CommandHelpViewPermission.MEMBER_KICK;
        } else if (this.member.getPermissions().contains(Permission.MESSAGE_MANAGE)) {
            return CommandHelpViewPermission.MANAGE_MESSAGE;
        } else if (this.member.getPermissions().contains(Permission.MANAGE_CHANNEL)) {
            return CommandHelpViewPermission.MANAGE_CHANNEL;
        } else if (this.member.getPermissions().contains(Permission.VIEW_AUDIT_LOGS)) {
            return CommandHelpViewPermission.VIEW_AUDIT_LOG;
        } else if (this.member.getPermissions().contains(Permission.BAN_MEMBERS)) {
            return CommandHelpViewPermission.MEMBER_BAN;
        } else {
            return CommandHelpViewPermission.EVERYONE;
        }
    }

    public int getCommandHelpViewPermissionValue() {
        switch (this.getCommandHelpViewPermission()) {
            case EVERYONE:
                return 0;
            case MANAGE_MESSAGE:
                return 1;
            case MANAGE_CHANNEL:
                return 2;
            case MEMBER_KICK:
                return 3;
            case MEMBER_BAN:
                return 4;
            case MANAGE_ROLE:
                return 5;
            case VIEW_AUDIT_LOG:
                return 6;
            case MANAGE_SERVER:
                return 7;
            case ADMINISTRATOR:
                return 8;
        }
        return 0;
    }

}
