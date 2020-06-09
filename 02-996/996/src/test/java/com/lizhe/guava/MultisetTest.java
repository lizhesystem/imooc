package com.lizhe.guava;

import com.google.common.collect.HashMultiset;
import com.google.common.primitives.Chars;
import org.junit.Test;

import java.util.List;

/**
 * 实现：使用Multiset统计一首古诗的文字出现频率
 *
 * @author lz
 * @create 2020/6/2
 */
public class MultisetTest {

    private static final String text =
            "《南陵别儿童入京》" +
                    "白酒新熟山中归，黄鸡啄黍秋正肥。" +
                    "呼童烹鸡酌白酒，儿女嬉笑牵人衣。" +
                    "高歌取醉欲自慰，起舞落日争光辉。" +
                    "游说万乘苦不早，著鞭跨马涉远道。" +
                    "会稽愚妇轻买臣，余亦辞家西入秦。" +
                    "仰天大笑出门去，我辈岂是蓬蒿人。";

    @Test
    public void handle() {
        // multiset创建
        HashMultiset<Object> mu = HashMultiset.create();

        // String 转换成 char数组
        char[] chars = text.toCharArray();

        // 先使用asList转换char为集合，然后遍历集合数组, 添加到multiset集合
        mu.addAll(Chars.asList(chars));

        // 统计总字数
        System.out.println(mu.size());

        // 统计字符'人'有几个
        System.out.println(mu.count('人'));
    }
}
