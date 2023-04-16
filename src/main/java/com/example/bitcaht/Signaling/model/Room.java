package com.example.bitcaht.Signaling.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class Room {
    private String roomId;
    private String userId;

}
