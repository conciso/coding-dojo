package de.conciso.codingdojo.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.conciso.codingdojo.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Application.class})
public class HelloControllerIT {

  MockMvc mockMvc;

  @Autowired
  HelloController controller;

  @Autowired
  ObjectMapper mapper;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders
        .standaloneSetup(controller)
        .setMessageConverters(new MappingJackson2HttpMessageConverter(mapper))
        .build();
  }


  @Test
  public void getGreetingsShouldReturnEmptyList() throws Exception {
    MvcResult result = mockMvc
        .perform(get("/welcome")
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    String resultBody = result.getResponse().getContentAsString();
    assertThat(resultBody).isEqualTo("[]");
  }

  @Test
  public void getGreetingsReturnsPostedPersons() throws Exception {
    mockMvc.perform(post("/welcome")
        .characterEncoding("UTF-8")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON)
        .content("{ \"name\": \"Marvin\" }"))
        .andDo(print())
        .andExpect(status().isOk());

    MvcResult result = mockMvc
        .perform(get("/welcome")
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    String resultBody = result.getResponse().getContentAsString();
    assertThat(resultBody).isEqualTo("[{\"id\":1,\"message\":\"Hallo Marvin\"}]");
  }

  @Test
  public void postPersonShouldReturnHttpOk() throws Exception {

    mockMvc.perform(post("/welcome")
        .characterEncoding("UTF-8")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON)
        .content("{ \"name\": \"Marvin\" }"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("{\"id\":1,\"message\":\"Hallo Marvin\"}"));
  }

  /*
  private String createJson(Object toJson) throws JsonProcessingException {
    return mapper.writeValueAsString(toJson);
  }

  @Test
  public void deleteHoodConfigurationWithIllegalArgumentExceptionReturnsBadRequest()
      throws Exception {
    when(guiService.deleteHoodConfiguration(anyString()))
        .thenThrow(new IllegalArgumentException(TEST_MESSAGE));

    mockMvc.perform(delete("/gui/configuration/1")
        .characterEncoding("UTF-8")
        .accept(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON)
        .content(createJson(GuiTestdataCreator.createArticleRepresentation())))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().string("\"" + TEST_MESSAGE + "\""));
  }

  @Test
  public void putConfigurationWithIllegalArgumentExceptionReturnsBadRequest() throws Exception {
    when(guiService.updateHoodConfiguration(any(HoodConfiguration.class)))
        .thenThrow(new IllegalArgumentException(TEST_MESSAGE));

    mockMvc.perform(put("/gui/configuration")
        .characterEncoding("UTF-8")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(createJson(GuiTestdataCreator.createHoodConfigurationRepresentation())))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().string("\"" + TEST_MESSAGE + "\""));
  }

  @Test
  public void deleteArticleWithIllegalArgumentExceptionReturnsBadRequest() throws Exception {
    when(guiService.deleteArticle(anyString()))
        .thenThrow(new IllegalArgumentException(TEST_MESSAGE));

    mockMvc.perform(delete("/gui/article/1")
        .characterEncoding("UTF-8")
        .accept(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON)
        .content(createJson(GuiTestdataCreator.createArticleRepresentation())))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().string("\"" + TEST_MESSAGE + "\""));
  }

  @Test
  public void createConfigurationValidatesInput() throws Exception {
    when(guiService.createNewHoodConfiguration(any(HoodConfiguration.class)))
        .thenAnswer(invocation -> Optional.of(invocation.getArgument(0)));

    HoodConfigurationRepresentation configuration = createInvalidHoodConfigurationRepresentation();

    MvcResult result = mockMvc
        .perform(post("/gui/configuration")
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(createJson(configuration)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andReturn();

    String resultBody = result.getResponse().getContentAsString();
    assertThat(resultBody).contains("hoodConfigurationId");
  }

  @Test
  public void updateConfigurationValidatesInput() throws Exception {
    when(guiService.updateHoodConfiguration(any(HoodConfiguration.class)))
        .thenAnswer(invocation -> Optional.of(invocation.getArgument(0)));

    HoodConfigurationRepresentation configuration = createInvalidHoodConfigurationRepresentation();

    MvcResult result = mockMvc
        .perform(put("/gui/configuration")
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(createJson(configuration)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andReturn();

    String resultBody = result.getResponse().getContentAsString();
    assertThat(resultBody).contains("hoodConfigurationId");
  }

  @Test
  public void updateArticleValidatesInput() throws Exception {
    when(guiService.updateArticlesHoodConfiguration(any(Article.class)))
        .thenAnswer(invocation -> Optional.of(invocation.getArgument(0)));

    ArticleRepresentation article = createInvalidArticleRepresentation();

    MvcResult result = mockMvc
        .perform(put("/gui/article")
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(createJson(article)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andReturn();

    String resultBody = result.getResponse().getContentAsString();
    assertThat(resultBody).contains("articleNumber");
  }

  private HoodConfigurationRepresentation createInvalidHoodConfigurationRepresentation() {
    HoodConfigurationRepresentation configuration = GuiTestdataCreator
        .createHoodConfigurationRepresentation();
    configuration = ImmutableHoodConfigurationRepresentation.copyOf(configuration)
        .withHoodConfigurationId("+".repeat(260));

    return configuration;
  }

  private ArticleRepresentation createInvalidArticleRepresentation() {
    ArticleRepresentation article = GuiTestdataCreator
        .createArticleRepresentation();
    article = ImmutableArticleRepresentation.copyOf(article)
        .withArticleNumber("§$%&");

    return article;
  }

*/
}