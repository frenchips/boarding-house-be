package com.adk.kost.response;

import com.adk.kost.constant.ResponseStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse<T> {
    @NonNull
    private ResponseStatus responseStatus;
    @NonNull
    private String status;
    @NonNull
    private String message;
    @NonNull
    private Integer pageNo;
    @NonNull
    private Integer pageSize;
    @NonNull
    private Integer totalDataInPage;
    @NonNull
    private Long totalData;
    @NonNull
    private Integer totalPages;
    @NonNull
    private List<T> listData;

}
