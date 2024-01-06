package com.vigyanshaala.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Response<T> {
    private int statusCode;

    private String statusMessage;


    private int pages;

    private T data;

    public Response(int statusCode,int pages, String statusMessage, T data)
    {
        this.statusCode=statusCode;

        this.pages=pages;

        this.statusMessage=statusMessage;

        this.data=data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }


    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }




    public T getData(){return data;}
    public void setData(T data){
        this.data=data;
    }



}
