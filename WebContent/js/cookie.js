/**
 * 设置cookie
 *
 * @param time    有效时间（单位:ms）
 */
function setCookie(key, value, time) {
    if (time == null) {
        document.cookie = key + "=" + value;
    } else {
        // 有效时间：默认一天
        time = 24 * 60 * 60 * 1000;
        var expires = new Date();
        expires.setTime(expires.getTime() + time)
        document.cookie = key + "=" + value + ";expires=" + expires.toGMTString();
    }
}

function getCookie(key) {
    var arr, reg = new RegExp("(^| )" + key + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) {
        return unescape(arr[2]);
    } else {
        return null;
    }
}

function clearCookie(key) {
    setCookie(key, "", 0)
}

function clearAllCookie() {
    var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
    if (keys) {
        for (var i = 0; i < keys.length; i++) {
            clearCookie(keys[i])
        }
    }
}