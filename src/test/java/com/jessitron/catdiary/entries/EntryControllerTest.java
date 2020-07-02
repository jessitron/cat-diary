package com.jessitron.catdiary.entries;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jessitron.catdiary.cats.CatName;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest // because WebMvcTest can't find a repository bean, so we can't get a controller
                // without loading the whole thing
@AutoConfigureMockMvc // secret "do things right" according to Ted
public class EntryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void getEntriesFailsWhenNotLoggedIn() throws Exception {

    mockMvc.perform(get("/entries").sessionAttr("catName", new CatName("pixie"))) //
        .andExpect(status().is4xxClientError());
     //   .andExpect(view().name("entries"))
     //   .andExpect(model().attributeExists("entries"));
  }

}