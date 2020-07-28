package me.hfox.craftbot.protocol.play.server.data.command;

import me.hfox.craftbot.utils.ToStringBuilder;

public class CommandNodeFlags {

    private final CommandNodeType nodeType;
    private final boolean executable;
    private final boolean redirect;
    private final boolean suggestions;

    public CommandNodeFlags(CommandNodeType nodeType, boolean executable, boolean redirect, boolean suggestions) {
        this.nodeType = nodeType;
        this.executable = executable;
        this.redirect = redirect;
        this.suggestions = suggestions;
    }

    public CommandNodeType getNodeType() {
        return nodeType;
    }

    public boolean isExecutable() {
        return executable;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public boolean isSuggestions() {
        return suggestions;
    }

    @Override
    public String toString() {
        return ToStringBuilder.build(this).deepArray(true).parents(true).reflective(true).simpleName(true).toString();
    }

}
