package com.github.Neelic.demo.service;

import com.github.Neelic.demo.javarushclient.dto.GroupDiscussionInfo;
import com.github.Neelic.demo.repository.entity.GroupSub;

public interface GroupSubService {

    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);
}
