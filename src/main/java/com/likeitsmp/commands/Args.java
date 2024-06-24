package com.likeitsmp.commands;

import java.util.LinkedList;
import java.util.Stack;

import org.bukkit.inventory.meta.BookMeta;

public final class Args
{
    private Args() { }

    public static String[] resplit(String[] rawArgs)
    {
        return split(rejoined(rawArgs));
    }

    public static String rejoined(String[] args)
    {
        return String.join(" ", args);
    }

    public static String[] split(String argText)
    {
        char[] argTextChars = argText.toCharArray();
        int argStart = 0;
        LinkedList<String> args = new LinkedList<>();
        Stack<Character> brackets = new Stack<>();
        boolean escape = false;

        for (int i = 0; i < argTextChars.length; i++)
        {
            char character = argTextChars[i];
            if (brackets.isEmpty()) switch (character)
            {
                case '[':
                case '{':
                case '\"':
                case '\'':
                    brackets.add(character);
                    break;
                
                case ' ':
                    int argEnd = i + 1;
                    args.add(argText.substring(argStart, argEnd));
                    argStart = argEnd;
                    break;
            }
            else
            {
                char bracket = brackets.peek();
                switch (bracket)
                {
                    case '\'':
                    case '\"':
                        if (escape)
                        {
                            escape = false;
                        }
                        else if (character == '\\')
                        {
                            escape = true;
                        }
                        else if (character == bracket)
                        {
                            brackets.pop();
                        }
                        break;

                    case '[':
                        if (character == ']')
                        {
                            brackets.pop();
                        }
                        break;

                    case '{':
                        if (character == '}')
                        {
                            brackets.pop();
                        }
                        break;
                }
            }
        }

        if (argStart < argTextChars.length)
        {
            args.add(argText.substring(argStart, argTextChars.length));
        }

        return args.toArray(new String[args.size()]);
    }

    public static String mergedPagesOf(BookMeta book)
    {
        return String.join("\n\n----\n\n", book.getPages());
    }
}
