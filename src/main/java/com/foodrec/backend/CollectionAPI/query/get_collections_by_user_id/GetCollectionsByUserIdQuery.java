package com.foodrec.backend.CollectionAPI.query.get_collections_by_user_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.dto.CollectionDTO;
import org.springframework.data.domain.Page;

public class GetCollectionsByUserIdQuery implements Command<Page<CollectionDTO>> {
}
