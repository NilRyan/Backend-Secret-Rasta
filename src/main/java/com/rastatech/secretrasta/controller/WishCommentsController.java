package com.rastatech.secretrasta.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes/{wish_id}/comments")
@RequiredArgsConstructor
public class WishCommentsController {
}
