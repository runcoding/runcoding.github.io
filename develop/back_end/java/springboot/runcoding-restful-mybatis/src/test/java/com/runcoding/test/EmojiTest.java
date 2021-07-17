package com.runcoding.test;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import com.vdurmont.emoji.EmojiParser.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2019-01-26 12:46
 * @description emoji
 * Copyright (C), 2017-2019,
 **/
public class EmojiTest {

    @Test
    public void parseToUnicodeTest(){
        String str = "An :grinning:awesome :smiley:string &#128516;with a few :wink:emojis!";
        String result = EmojiParser.parseToUnicode(str);
        System.out.println(result);
        //An 😀awesome 😃string 😄with a few 😉emojis!
    }

    @Test
    public void parseToAliasesTest(){
        String str = "An 😀awesome 😃string with a few 😉emojis!";
        System.out.println(EmojiParser.parseToAliases(str));
        //An :grinning:awesome :smiley:string with a few :wink:emojis!
        System.out.println(EmojiParser.parseToAliases(str, FitzpatrickAction.REMOVE));

        str = "Here is a boy: \uD83D\uDC66\uD83C\uDFFF!";
        System.out.println(EmojiParser.parseToAliases(str));
        //Here is a boy: :boy|type_6:!
        System.out.println(EmojiParser.parseToAliases(str, FitzpatrickAction.PARSE));
        //Here is a boy: :boy|type_6:!
        System.out.println(EmojiParser.parseToAliases(str, FitzpatrickAction.REMOVE));
        //Here is a boy: :boy:!
        System.out.println(EmojiParser.parseToAliases(str, FitzpatrickAction.IGNORE));
        //Here is a boy: :boy:🏿!
    }

    @Test
    public void parseToHtmlDecimalTest(){
        String str = "An 😀awesome 😃string with a few 😉emojis!";

        String resultDecimal = EmojiParser.parseToHtmlDecimal(str);
        System.out.println(resultDecimal);
        // Prints:
        // "An &#128512;awesome &#128515;string with a few &#128521;emojis!"

        String resultHexadecimal = EmojiParser.parseToHtmlHexadecimal(str);
        System.out.println(resultHexadecimal);
        // Prints:
        // "An &#x1f600;awesome &#x1f603;string with a few &#x1f609;emojis!"

         str = "Here is a boy: \uD83D\uDC66\uD83C\uDFFF!";
        System.out.println(EmojiParser.parseToHtmlDecimal(str));
        System.out.println(EmojiParser.parseToHtmlDecimal(str, FitzpatrickAction.PARSE));
        System.out.println(EmojiParser.parseToHtmlDecimal(str, FitzpatrickAction.REMOVE));
        // Print 3 times: "Here is a boy: &#128102;!"
        System.out.println(EmojiParser.parseToHtmlDecimal(str, FitzpatrickAction.IGNORE));
        // Prints: "Here is a boy: &#128102;🏿!"
    }

    @Test
    public void  removeAllEmojisTest(){
        String str = "An 😀awesome 😃string with a few 😉emojis!";
        Collection<Emoji> collection = new ArrayList<>();
        collection.add(EmojiManager.getForAlias("wink")); // This is 😉

        System.out.println(EmojiParser.removeAllEmojis(str));
        //An awesome string with a few emojis!
        System.out.println(EmojiParser.removeAllEmojisExcept(str, collection));
        //An awesome string with a few 😉emojis!
        System.out.println(EmojiParser.removeEmojis(str, collection));
        //An 😀awesome 😃string with a few emojis!
    }

}
