package com.rogerfitness.workoutsystem.responses;

public class ApiResponse <T>{
    private int statusCode;
    private T result;

    public static <T> ApiResponseBuilder<T> builder(){return new ApiResponseBuilder<>();}
    public ApiResponseBuilder<T> toBuilder(){
        return (new ApiResponseBuilder()).statusCode(this.statusCode).result(this.result);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


    public static class ApiResponseBuilder<T>{
        private int statusCode;
        private T result;

        ApiResponseBuilder() {
        }

        public ApiResponseBuilder<T> statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }
        public ApiResponseBuilder<T> result(T result) {
            this.result = result;
            return this;
        }
        public ApiResponse<T> build(){
            return new ApiResponse(this.statusCode,this.result);
        }

    @Override
    public String toString() {
        return "ApiResponse.ApiResponseBuilder{" +
                "statusCode=" + this.statusCode +
                ", result=" + this.result +
                '}';
    }
}
}


