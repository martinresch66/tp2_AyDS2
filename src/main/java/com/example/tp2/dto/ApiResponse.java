package com.example.tp2.dto;
/*ApiResponse<T> es el molde para construir 
la respuesta que le devuelves al cliente, 
asegurando que toda respuesta de tu API tenga siempre 
la misma estructura (status, messege, data).   */
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    public ApiResponse(int status,String message,T data){
        this.status=status;
        this.message=message;
        this.data=data;
    }

    public int getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setStatus(int status) {
        this.status = status;
    }

}
