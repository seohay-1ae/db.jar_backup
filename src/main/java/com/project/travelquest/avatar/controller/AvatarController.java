package com.project.travelquest.avatar.controller;

import com.project.travelquest.avatar.dto.AvatarPresetDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AvatarController {

    @GetMapping("/avatar")
    public String showAvatar(Model model) {
        // 예시 아바타 데이터
        AvatarPresetDto preset = new AvatarPresetDto();
        preset.setSkin("skins/07.svg");
        preset.setFace("faces/02.svg");
        preset.setHands("hands/10.png");
        preset.setBottoms("bottoms/10.svg");
        preset.setTops("tops/02.svg");
        preset.setHats("hats/03.svg");

        model.addAttribute("preset", preset);
        return "avatar"; // avatar.jsp
    }
}
