package com.lizhe.web.controller;


import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * RunWith注解作用：
 * 因为SpringRunner.class继承了SpringJUnit4ClassRunner.class且没有进行任何修改
 * 所以@RunWith(SpringRunner.class)基本等同于@RunWith(SpringJUnit4ClassRunner.class)
 *
 * 注解的作用：
 * 让测试在Spring容器环境下执行。如测试类中无此注解，将导致service,dao等自动注入失败。
 */

// SpringBootTest传入入口类,这个classes其实可以省略
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    /**
     * 伪造环境
     */
    @Autowired
    private WebApplicationContext wac;

    // 伪造mvc环境
    private MockMvc mockMvc;

    // 该方法在每个测试用例执行之前执行
    @Before
    public void setup() {
        // 构建一个伪造的mvc环境
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * 文件上传测试用例
     *
     * @throws Exception
     */
    @Test
    public void whenUploadSuccess() throws Exception {

        /**
         *  相当于用D盘的hello.txt，模拟上传个文件。
         */
        File file = new File("D:\\", "hello.txt");
        FileInputStream fis = new FileInputStream(file);
        byte[] content = new byte[fis.available()];
        fis.read(content);

        /**
         *  参数：
         *  name  请求参数，相当于<input>标签的的`name`属性
         *  originalName 上传的文件名称
         *  contentType  上传文件需指定为`multipart/form-data`
         *  content      字节数组，上传文件的内容
         */
        String fileKey = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file")
                .file(new MockMultipartFile("file", "hello.txt", "multipart/form-data", content)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(fileKey);
    }


    /*
     *  SpringBoot之MockMvc单元测试：https://zhuanlan.zhihu.com/p/61342833
     * 1、mockMvc.perform执行一个请求。
     * 2、MockMvcRequestBuilders.get("XXX")构造一个请求。
     * 3、ResultActions.param添加请求传值
     * 4、ResultActions.accept(MediaType.TEXT_HTML_VALUE))设置返回类型
     * 5、ResultActions.andExpect添加执行完成后的断言。
     * 6、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
     *   比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
     * 7、ResultActions.andReturn表示执行完成后返回相应的结果。
     *    contentType表示请求的请求头
     */
    @Test
    public void whenQuerySuccess() throws Exception {
        // 测试用例发请求,请求user接口的get请求
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .param("username", "lz")
                .param("password", "null")
                .param("search", "001")
                .param("age", "20")
                .param("page", "2")
                .param("size", "50")
                .param("sort", "age,desc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                // 返回结果
                .andExpect(MockMvcResultMatchers.status().isOk())
                // jsonPath用法https://www.cnblogs.com/youring2/p/10942728.html
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
        // [{"username":null,"password":null},{"username":null,"password":null},{"username":null,"password":null}]
    }

    /**
     * restFul风格请求成功测试用例
     *
     * @throws Exception
     */
    @Test
    public void whenGetInfoSuccess() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 拿到username的属性和lz的value
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("lz"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result); //返回： {"username":"lz","password":null}

    }

    /**
     * restFul风格请求失败测试用例,返回4xx错误
     *
     * @throws Exception
     */
    @Test
    public void whenGetInfoFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/a")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    /**
     * 创建请求测试
     *
     * @throws Exception
     */
    @Test
    public void whenCreateSuccess() throws Exception {
        // 拼接字符串
        //String content = "{\"username\":\"tom\",\"password\":null,\"birthday\":" + new Date().getTime() + "}";

        JSONObject json = new JSONObject();
        json.put("username", "tom");
        json.put("password", "null");
        json.put("birthday", new Date().getTime() + "");
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    /**
     * 创建失败请求测试
     */
    @Test
    public void whenCreateFail() throws Exception {
        String content = "{\"username\":\"tom\",\"password\":null,\"birthday\":" + new Date().getTime() + "}";
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                //.andExpect(MockMvcResultMatchers.status().isOk())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);

    }

    /**
     * update修改测试用例
     *
     * @throws Exception
     */
    @Test
    public void whenUpdateSuccess() throws Exception {
        // 往后推迟一年的时间来验证@Past的校验
        Date date = new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        //Date date = new Date();
        String content = "{\"username\":\"tom\",\"password\":110,\"birthday\":" + date.getTime() + ",\"search\":\"abc\"}";
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    /**
     * 删除的测试用例
     *
     * @throws Exception
     */
    @Test
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void demoTest() {
        System.out.println("time" + LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        //Date date = new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

    }
}
