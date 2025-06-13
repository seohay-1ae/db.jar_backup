<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Avatar</title>
    <style>
        .avatar {
            position: relative;
            width: 200px;
            height: 200px;
        }

        .layer {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
        }

        .hands { z-index: 10; }
        .skin { z-index: 20; }
        .line { z-index: 30; }
        .bottoms { z-index: 40; }
        .tops { z-index: 50; }
        .face { z-index: 60; }
        .hats { z-index: 70; }
    </style>
</head>
<body>
<h2>나의 아바타</h2>

<div class="avatar">
    <img src="/avatars/line.svg" class="layer line" />
    <img src="/avatars/${preset.skin}" class="layer skin" />
    <img src="/avatars/${preset.face}" class="layer face" />
    <img src="/avatars/${preset.hands}" class="layer hands" />
    <img src="/avatars/${preset.bottoms}" class="layer bottoms" />
    <img src="/avatars/${preset.tops}" class="layer tops" />
    <img src="/avatars/${preset.hats}" class="layer hats" />
</div>
</body>
</html>
