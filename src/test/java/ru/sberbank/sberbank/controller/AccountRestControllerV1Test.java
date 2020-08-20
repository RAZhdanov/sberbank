package ru.sberbank.sberbank.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.sberbank.sberbank.AbstractTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AccountRestControllerV1Test extends AbstractTest {

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
    void transferMoneyBetweenAccounts_WithAppropriateParams_Then_Return_200_Ok_Ver1() throws Exception {
        this.mockMvc.
                perform(post("/api/v1/accounts/sendMoney/1/2/10").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
    }
    @Test
    void transferMoneyBetweenAccounts_WithAppropriateParams_Then_Return_200_Ok_Ver2() throws Exception {
        this.mockMvc.
                perform(post("/api/v1/accounts/sendMoney/1/2/10.0").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
    }

    @Test
    void transferMoneyBetweenNonExistentAccounts_ButWithAppropriateParams_Then_Return_404_NotFound() throws Exception {
        this.mockMvc.
                perform(post("/api/v1/accounts/sendMoney/100/102/10.0").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());
    }

    @Test
    void transferMoneyBetweenAccounts_ButWithEstablishedWrongHttpGetMethod_Then_Return_405_MethodNotAllowed() throws Exception {
        this.mockMvc.
                perform(get("/api/v1/accounts/sendMoney/1/2/10").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isMethodNotAllowed());
    }

}
