package com.bot.dispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bot.commands.ICommand;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandDispatcher 
{
    private final Map<String, ICommand> _commands = new HashMap<>();
    private final List<IMessageHook> _messageHooks = new ArrayList<>();

    public CommandDispatcher register(ICommand command)
    {
        _commands.put(command.name(), command);
        return this;
    }

    public void registerMessageHook(IMessageHook hook) 
    {
        _messageHooks.add(hook);
    }

    public void execute(MessageReceivedEvent event) 
    {
        String content = event.getMessage().getContentRaw();
        //System.out.println("RAW CONTENT: [" + content + "]");

        String trimmed = content.trim();
        //System.out.println("TRIMMED: [" + trimmed + "]");

        String[] parts = trimmed.split("\\s+");
        //System.out.println("PARTS[0]: [" + parts[0] + "]");

        //System.out.println("COMANDOS REGISTRADOS: " + _commands.keySet());

        ICommand command = _commands.get(parts[0]);

        //System.out.println("COMANDO ENCONTRADO? " + (command != null));

        if (command != null) 
            command.execute(parts, event);

        executeMessageHooks(event);
    }

    private void executeMessageHooks(MessageReceivedEvent event)
    {
        String discordId = event.getAuthor().getId();
        String serverId = event.isFromGuild() ? event.getGuild().getId() : null;

        if (serverId == null)
            return;

        _messageHooks.forEach(hook -> hook.onMessageReceived(discordId, serverId));
    }

    @FunctionalInterface
    public interface IMessageHook 
    {
        void onMessageReceived(String discordId, String serverId);
    }
}
