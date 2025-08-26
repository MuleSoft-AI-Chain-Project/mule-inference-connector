package com.mulesoft.connectors.inference.internal.helpers.response;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;

public class ResponseWrapper {

    private TextResponseDTO responseDTO;
    private String nativeResponse;

    public ResponseWrapper(TextResponseDTO responseDTO, String nativeResponse){
        this.responseDTO = responseDTO;
        this.nativeResponse = nativeResponse;
    }


    public TextResponseDTO getResponseDTO(){
        return this.responseDTO;
    }

    public String getNativeResponse(){
        return this.nativeResponse;
    }

}