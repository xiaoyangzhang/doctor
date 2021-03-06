package com.yhyt.health.app.controller;

import com.yhyt.health.result.AppResult;

public interface ItemApi {

    AppResult getItems(String token,Long userId,String doctorState);

    AppResult getItem(String token,Long taskId);

    AppResult updateItemState(String token,Long userId, Long taskId,String doctorState);

    AppResult getItemRecords(String token,String roomId);

    AppResult getTaskServiceState(String token, Long orderId);
}
