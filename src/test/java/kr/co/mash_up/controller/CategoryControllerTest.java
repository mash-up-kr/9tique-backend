package kr.co.mash_up.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.mash_up.builder.CategoryBuilder;
import kr.co.mash_up.nine_tique.NineTiqueApplication;
import kr.co.mash_up.nine_tique.controller.CategoryController;
import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.service.CategorySservice;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_CATEGORY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * 컨트롤러의 수행 흐름을 테스트
 * http://devjms.tistory.com/99
 * <p>
 * http://lng1982.tistory.com/152 -> Mockito 번역있음
 */
@WebAppConfiguration
//@WithMockUser(username = "opklnm102")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NineTiqueApplication.class})
@ActiveProfiles(profiles = "test")
public class CategoryControllerTest {

    public static final String TEST_MAIN = "testMain";
    public static final String TEST_SUB = "testSub";

    Logger logger = Logger.getLogger(this.getClass());

//    @Autowired
//    private WebApplicationContext webApplicationContext;  // WebApplicationContext 주입

    private MockMvc mockMvc;

    @Autowired
    private CategoryController categoryController;

    @Autowired
    private CategorySservice categoryService;

    @Before
    public void setup() throws Exception {
        // 통합 테스트
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .build();

        // 유닛 테스트
        mockMvc = standaloneSetup(categoryController)
                .build();
    }

    @After
    public void tearDown(){

    }

//    @Test
//    public void testAdd() throws Exception {
//
////        given(categoryService.create(category)).willReturn(category);
//
//        // when절에 해당하는 메소드를 호출할 경우 then절에 정의된 내용 반환
////        when(categoryService.create(category)).thenReturn(category);
//
//        Category category = new CategoryBuilder()
//                .withMain("main1")
//                .withSub("sub1")
//                .withEnable(true)
//                .build();
//
//        String jsonString = this.jsonStringFromObject(category);
//
//        /**
//         * Perform -> 요청 사전 조건
//         * Expect -> 응단 관련 테스트
//         * Do -> 테스트시 직접 실행
//         * Return -> 테스트 결과 반환
//         */
//        this.mockMvc.perform(post(API_CATEGORY)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonString))
//                .andExpect(handler().handlerType(CategoryController.class))  //요청에 매핑된 클래스 확인
//                .andExpect(handler().methodName("add"))  // 요청에 매핑된 메소드 확인
//                .andExpect(status().isOk());


//        this.mockMvc.perform(get("/category/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("categories"))
//                .andExpect(model().attribute("categories", Matchers.hasSize(1)))
//                .andExpect(model().attribute("categories", Matchers.contains(Matchers.samePropertyValuesAs(category))));
//    }

//    @Test
//    public void testUpdate() throws Exception {
//        Long id = 3L;
//        Category category = new CategoryBuilder()
//                .withId(id)
//                .withMain("main1")
//                .withSub("sub1")
//                .withEnable(true)
//                .build();
//        String jsonString = this.jsonStringFromObject(category);
//
//        this.mockMvc.perform(put(API_CATEGORY + "/{id}", id)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonString))
//                .andExpect(handler().handlerType(CategoryController.class))
//                .andExpect(handler().methodName("update"))
//                .andExpect(status().isOk());
//    }

    @Test
    public void testDelete() throws Exception {
        Long id = 1L;

        this.mockMvc.perform(delete(API_CATEGORY + "/{id}", id))
                .andExpect(handler().handlerType(CategoryController.class))
                .andExpect(handler().methodName("delete"))
                .andExpect(status().isOk());
    }

    @Test
    public void testList() throws Exception {

//        when(categoryService.findCategories()).thenReturn(anyList());

        MvcResult result = mockMvc.perform(get(API_CATEGORY))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(handler().handlerType(CategoryController.class))
                .andExpect(handler().methodName("list"))
                .andReturn();

        logger.info(result.getResponse().getContentAsString());

//        verify(categoryService).findCategories();
//        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void testDetail() throws Exception {
        Long id = 2L;

        mockMvc.perform(get(API_CATEGORY + "/{id}", id))
                .andExpect(handler().handlerType(CategoryController.class))
                .andExpect(handler().methodName("detail"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    private String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
