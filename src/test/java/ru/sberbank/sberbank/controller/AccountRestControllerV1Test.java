package ru.sberbank.sberbank.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.sberbank.sberbank.AbstractTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AccountRestControllerV1Test extends AbstractTest {

//    @Test
//    public void getAccountList() throws Exception {
//        String uri = "/api/v1/accounts";
//
//        MockHttpServletRequestBuilder httpServletRequestBuilder = MockMvcRequestBuilders.get(uri);
//        MvcResult mvcResult = mockMvc.perform(httpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON)
//            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        CollectionModel<SberAccountEntity> sberAccountEntities = new CollectionModel<>(Arrays.asList(
//                new SberAccountEntity(),
//                new SberAccountEntity(),
//                new SberAccountEntity()
//        ));
//        sberAccountEntities = super.mapFromJson(content, sberAccountEntities.getClass());
//        assertTrue(sberAccountEntities.getContent().size() > 0);
//    }


    @Test
    void transferMoneyBetweenAccounts_With_Inappropriate_SourceAccountURI_ID_Then_Return_404_NotFound() throws Exception {

        this.mockMvc.
                perform(post("/api/v1/accounts/sendMoney/1abc/2/1.0").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());

    }

    @Test
    void transferMoneyBetweenAccounts_With_Inappropriate_TargetAccountURI_ID_Then_Return_404_NotFound() throws Exception {
        this.mockMvc.
                perform(post("/api/v1/accounts/sendMoney/1/2abc/1.0").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());
    }

    @Test
    void transferMoneyBetweenAccounts_With_Inappropriate_Amount_Then_Return_404_NotFound() throws Exception {
        this.mockMvc.
                perform(post("/api/v1/accounts/sendMoney/1/2/1abc").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());
    }

    @Test
    void transferMoneyBetweenAccounts_WithAppropriateParams_Then_Return_200_Ok() throws Exception {
        this.mockMvc.
                perform(post("/api/v1/accounts/sendMoney/1/2/10").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
    }

}
