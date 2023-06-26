package com.foodrec.backend.CollectionAPI.query.get_all_collections_by_user_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.dto.CollectionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCollectionsByUserIdQuery implements Command<Page<CollectionDTO>> {
    private int pageNumber;
    private int pageSize;
    private String userId;
}
