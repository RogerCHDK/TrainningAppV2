package com.rogerfitness.workoutsystem.responses;

public class ApiResponse<T> {
    private int statusCode;
    private T result;

    public static <T> ApiResponseBuilder<T> builder() {
        return new ApiResponseBuilder<>();
    }

    public ApiResponseBuilder<T> toBuilder() {
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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ApiResponse)) {
            return false;
        } else {
            ApiResponse<?> other = (ApiResponse) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getStatusCode() != other.getStatusCode()) {
                return false;
            } else {
                Object this$result = this.getResult();
                Object other$result = other.getResult();
                if (this$result == null) {
                    if (other$result != null) {
                        return false;
                    }
                } else if (!this$result.equals(other$result)) {
                    return false;
                }
                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ApiResponse;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + this.getStatusCode();
        Object $result = this.getResult();
        result = result * 59 + ($result == null ? 43 : $result.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "statusCode=" + statusCode +
                ", result=" + result +
                '}';
    }

    public ApiResponse() {
    }

    public ApiResponse(int statusCode, T result) {
        this.statusCode = statusCode;
        this.result = result;
    }

    public static class ApiResponseBuilder<T> {
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

        public ApiResponse<T> build() {
            return new ApiResponse(this.statusCode, this.result);
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


