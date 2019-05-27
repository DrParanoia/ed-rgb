package com.bmc.elite.mappings;

import com.bmc.elite.KeyHIDCodes;
import com.bmc.elite.VirtualKeyCodes;
import com.bmc.elite.config.LedKeys;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class KeyMaps {

    public static BiMap<Integer, Integer> VIRTUAL_HID = new ImmutableBiMap.Builder<Integer, Integer>()
//          .put(VirtualKeyCodes.VK_ABNT_C1, KeyHIDCodes.KEY_ABNT_C1) // Abnt C1
//          .put(VirtualKeyCodes.VK_ABNT_C2, KeyHIDCodes.KEY_ABNT_C2) // Abnt C2
            .put(VirtualKeyCodes.VK_ADD, KeyHIDCodes.KEY_KPPLUS) // Numpad +
//          .put(VirtualKeyCodes.VK_ATTN, KeyHIDCodes.KEY_ATTN) // Attn
            .put(VirtualKeyCodes.VK_BACK, KeyHIDCodes.KEY_BACKSPACE) // Backspace
            .put(VirtualKeyCodes.VK_CANCEL, KeyHIDCodes.KEY_PAUSE) // Break
//          .put(VirtualKeyCodes.VK_CLEAR, KeyHIDCodes.KEY_CLEAR) // Clear
//          .put(VirtualKeyCodes.VK_CRSEL, KeyHIDCodes.KEY_CRSEL) // Cr Sel
            .put(VirtualKeyCodes.VK_DECIMAL, KeyHIDCodes.KEY_KPDOT) // Numpad .
            .put(VirtualKeyCodes.VK_DIVIDE, KeyHIDCodes.KEY_KPSLASH) // Numpad /
//          .put(VirtualKeyCodes.VK_EREOF, KeyHIDCodes.KEY_EREOF) // Er Eof
            .put(VirtualKeyCodes.VK_ESCAPE, KeyHIDCodes.KEY_ESC) // Esc
//          .put(VirtualKeyCodes.VK_EXECUTE, KeyHIDCodes.KEY_EXECUTE) // Execute
//          .put(VirtualKeyCodes.VK_EXSEL, KeyHIDCodes.KEY_EXSEL) // Ex Sel
//          .put(VirtualKeyCodes.VK_ICO_CLEAR, KeyHIDCodes.KEY_ICO_CLEAR) // IcoClr
//          .put(VirtualKeyCodes.VK_ICO_HELP, KeyHIDCodes.KEY_ICO_HELP) // IcoHlp
            .put(VirtualKeyCodes.VK_KEY_0, KeyHIDCodes.KEY_0) // 0
            .put(VirtualKeyCodes.VK_KEY_1, KeyHIDCodes.KEY_1) // 1
            .put(VirtualKeyCodes.VK_KEY_2, KeyHIDCodes.KEY_2) // 2
            .put(VirtualKeyCodes.VK_KEY_3, KeyHIDCodes.KEY_3) // 3
            .put(VirtualKeyCodes.VK_KEY_4, KeyHIDCodes.KEY_4) // 4
            .put(VirtualKeyCodes.VK_KEY_5, KeyHIDCodes.KEY_5) // 5
            .put(VirtualKeyCodes.VK_KEY_6, KeyHIDCodes.KEY_6) // 6
            .put(VirtualKeyCodes.VK_KEY_7, KeyHIDCodes.KEY_7) // 7
            .put(VirtualKeyCodes.VK_KEY_8, KeyHIDCodes.KEY_8) // 8
            .put(VirtualKeyCodes.VK_KEY_9, KeyHIDCodes.KEY_9) // 9
            .put(VirtualKeyCodes.VK_KEY_A, KeyHIDCodes.KEY_A) // A
            .put(VirtualKeyCodes.VK_KEY_B, KeyHIDCodes.KEY_B) // B
            .put(VirtualKeyCodes.VK_KEY_C, KeyHIDCodes.KEY_C) // C
            .put(VirtualKeyCodes.VK_KEY_D, KeyHIDCodes.KEY_D) // D
            .put(VirtualKeyCodes.VK_KEY_E, KeyHIDCodes.KEY_E) // E
            .put(VirtualKeyCodes.VK_KEY_F, KeyHIDCodes.KEY_F) // F
            .put(VirtualKeyCodes.VK_KEY_G, KeyHIDCodes.KEY_G) // G
            .put(VirtualKeyCodes.VK_KEY_H, KeyHIDCodes.KEY_H) // H
            .put(VirtualKeyCodes.VK_KEY_I, KeyHIDCodes.KEY_I) // I
            .put(VirtualKeyCodes.VK_KEY_J, KeyHIDCodes.KEY_J) // J
            .put(VirtualKeyCodes.VK_KEY_K, KeyHIDCodes.KEY_K) // K
            .put(VirtualKeyCodes.VK_KEY_L, KeyHIDCodes.KEY_L) // L
            .put(VirtualKeyCodes.VK_KEY_M, KeyHIDCodes.KEY_M) // M
            .put(VirtualKeyCodes.VK_KEY_N, KeyHIDCodes.KEY_N) // N
            .put(VirtualKeyCodes.VK_KEY_O, KeyHIDCodes.KEY_O) // O
            .put(VirtualKeyCodes.VK_KEY_P, KeyHIDCodes.KEY_P) // P
            .put(VirtualKeyCodes.VK_KEY_Q, KeyHIDCodes.KEY_Q) // Q
            .put(VirtualKeyCodes.VK_KEY_R, KeyHIDCodes.KEY_R) // R
            .put(VirtualKeyCodes.VK_KEY_S, KeyHIDCodes.KEY_S) // S
            .put(VirtualKeyCodes.VK_KEY_T, KeyHIDCodes.KEY_T) // T
            .put(VirtualKeyCodes.VK_KEY_U, KeyHIDCodes.KEY_U) // U
            .put(VirtualKeyCodes.VK_KEY_V, KeyHIDCodes.KEY_V) // V
            .put(VirtualKeyCodes.VK_KEY_W, KeyHIDCodes.KEY_W) // W
            .put(VirtualKeyCodes.VK_KEY_X, KeyHIDCodes.KEY_X) // X
            .put(VirtualKeyCodes.VK_KEY_Y, KeyHIDCodes.KEY_Y) // Y
            .put(VirtualKeyCodes.VK_KEY_Z, KeyHIDCodes.KEY_Z) // Z
            .put(VirtualKeyCodes.VK_MULTIPLY, KeyHIDCodes.KEY_KPASTERISK) // Numpad *
//          .put(VirtualKeyCodes.VK_NONAME, KeyHIDCodes.KEY_NONAME) // NoName
            .put(VirtualKeyCodes.VK_NUMPAD0, KeyHIDCodes.KEY_KP0) // Numpad 0
            .put(VirtualKeyCodes.VK_NUMPAD1, KeyHIDCodes.KEY_KP1) // Numpad 1
            .put(VirtualKeyCodes.VK_NUMPAD2, KeyHIDCodes.KEY_KP2) // Numpad 2
            .put(VirtualKeyCodes.VK_NUMPAD3, KeyHIDCodes.KEY_KP3) // Numpad 3
            .put(VirtualKeyCodes.VK_NUMPAD4, KeyHIDCodes.KEY_KP4) // Numpad 4
            .put(VirtualKeyCodes.VK_NUMPAD5, KeyHIDCodes.KEY_KP5) // Numpad 5
            .put(VirtualKeyCodes.VK_NUMPAD6, KeyHIDCodes.KEY_KP6) // Numpad 6
            .put(VirtualKeyCodes.VK_NUMPAD7, KeyHIDCodes.KEY_KP7) // Numpad 7
            .put(VirtualKeyCodes.VK_NUMPAD8, KeyHIDCodes.KEY_KP8) // Numpad 8
            .put(VirtualKeyCodes.VK_NUMPAD9, KeyHIDCodes.KEY_KP9) // Numpad 9
            .put(VirtualKeyCodes.VK_OEM_1, KeyHIDCodes.KEY_SEMICOLON) // OEM_1 (: ;)
//          .put(VirtualKeyCodes.VK_OEM_102, KeyHIDCodes.KEY_OEM_102) // OEM_102 (> <)
//          .put(VirtualKeyCodes.VK_OEM_2, KeyHIDCodes.KEY_OEM_2) // OEM_2 (? /)
            .put(VirtualKeyCodes.VK_OEM_3, KeyHIDCodes.KEY_GRAVE) // OEM_3 (~ `)
//          .put(VirtualKeyCodes.VK_OEM_4, KeyHIDCodes.KEY_OEM_4) // OEM_4 ({ [)
//          .put(VirtualKeyCodes.VK_OEM_5, KeyHIDCodes.KEY_OEM_5) // OEM_5 (| \)
//          .put(VirtualKeyCodes.VK_OEM_6, KeyHIDCodes.KEY_OEM_6) // OEM_6 (} ])
//          .put(VirtualKeyCodes.VK_OEM_7, KeyHIDCodes.KEY_OEM_7) // OEM_7 (" ')
//          .put(VirtualKeyCodes.VK_OEM_8, KeyHIDCodes.KEY_OEM_8) // OEM_8 (§ !)
//          .put(VirtualKeyCodes.VK_OEM_ATTN, KeyHIDCodes.KEY_OEM_ATTN) // Oem Attn
//          .put(VirtualKeyCodes.VK_OEM_AUTO, KeyHIDCodes.KEY_OEM_AUTO) // Auto
//          .put(VirtualKeyCodes.VK_OEM_AX, KeyHIDCodes.KEY_OEM_AX) // Ax
//          .put(VirtualKeyCodes.VK_OEM_BACKTAB, KeyHIDCodes.KEY_OEM_BACKTAB) // Back Tab
//          .put(VirtualKeyCodes.VK_OEM_CLEAR, KeyHIDCodes.KEY_OEM_CLEAR) // OemClr
//          .put(VirtualKeyCodes.VK_OEM_COMMA, KeyHIDCodes.KEY_OEM_COMMA) // OEM_COMMA (< ,)
//          .put(VirtualKeyCodes.VK_OEM_COPY, KeyHIDCodes.KEY_OEM_COPY) // Copy
//          .put(VirtualKeyCodes.VK_OEM_CUSEL, KeyHIDCodes.KEY_OEM_CUSEL) // Cu Sel
//          .put(VirtualKeyCodes.VK_OEM_ENLW, KeyHIDCodes.KEY_OEM_ENLW) // Enlw
//          .put(VirtualKeyCodes.VK_OEM_FINISH, KeyHIDCodes.KEY_OEM_FINISH) // Finish
//          .put(VirtualKeyCodes.VK_OEM_FJ_LOYA, KeyHIDCodes.KEY_OEM_FJ_LOYA) // Loya
//          .put(VirtualKeyCodes.VK_OEM_FJ_MASSHOU, KeyHIDCodes.KEY_OEM_FJ_MASSHOU) // Mashu
//          .put(VirtualKeyCodes.VK_OEM_FJ_ROYA, KeyHIDCodes.KEY_OEM_FJ_ROYA) // Roya
//          .put(VirtualKeyCodes.VK_OEM_FJ_TOUROKU, KeyHIDCodes.KEY_OEM_FJ_TOUROKU) // Touroku
//          .put(VirtualKeyCodes.VK_OEM_JUMP, KeyHIDCodes.KEY_OEM_JUMP) // Jump
//          .put(VirtualKeyCodes.VK_OEM_MINUS, KeyHIDCodes.KEY_OEM_MINUS) // OEM_MINUS (_ -)
//          .put(VirtualKeyCodes.VK_OEM_PA1, KeyHIDCodes.KEY_OEM_PA1) // OemPa1
//          .put(VirtualKeyCodes.VK_OEM_PA2, KeyHIDCodes.KEY_OEM_PA2) // OemPa2
//          .put(VirtualKeyCodes.VK_OEM_PA3, KeyHIDCodes.KEY_OEM_PA3) // OemPa3
//          .put(VirtualKeyCodes.VK_OEM_PERIOD, KeyHIDCodes.KEY_OEM_PERIOD) // OEM_PERIOD (> .)
//          .put(VirtualKeyCodes.VK_OEM_PLUS, KeyHIDCodes.KEY_OEM_PLUS) // OEM_PLUS (+ =)
//          .put(VirtualKeyCodes.VK_OEM_RESET, KeyHIDCodes.KEY_OEM_RESET) // Reset
//          .put(VirtualKeyCodes.VK_OEM_WSCTRL, KeyHIDCodes.KEY_OEM_WSCTRL) // WsCtrl
//          .put(VirtualKeyCodes.VK_PA1, KeyHIDCodes.KEY_PA1) // Pa1
//          .put(VirtualKeyCodes.VK_PACKET, KeyHIDCodes.KEY_PACKET) // Packet
//          .put(VirtualKeyCodes.VK_PLAY, KeyHIDCodes.KEY_PLAY) // Play
//          .put(VirtualKeyCodes.VK_PROCESSKEY, KeyHIDCodes.KEY_PROCESSKEY) // Process
            .put(VirtualKeyCodes.VK_RETURN, KeyHIDCodes.KEY_ENTER) // Enter
//          .put(VirtualKeyCodes.VK_SELECT, KeyHIDCodes.KEY_SELECT) // Select
//          .put(VirtualKeyCodes.VK_SEPARATOR, KeyHIDCodes.KEY_SEPARATOR) // Separator
            .put(VirtualKeyCodes.VK_SPACE, KeyHIDCodes.KEY_SPACE) // Space
            .put(VirtualKeyCodes.VK_SUBTRACT, KeyHIDCodes.KEY_MINUS) // Num -
            .put(VirtualKeyCodes.VK_TAB, KeyHIDCodes.KEY_TAB) // Tab
//          .put(VirtualKeyCodes.VK_ZOOM, KeyHIDCodes.KEY_ZOOM) // Zoom
//          .put(VirtualKeyCodes.VK__none_, KeyHIDCodes.KEY__none_) // no VK mapping
//          .put(VirtualKeyCodes.VK_ACCEPT, KeyHIDCodes.KEY_ACCEPT) // Accept
//          .put(VirtualKeyCodes.VK_APPS, KeyHIDCodes.KEY_APPS) // Context Menu
//          .put(VirtualKeyCodes.VK_BROWSER_BACK, KeyHIDCodes.KEY_BROWSER_BACK) // Browser Back
//          .put(VirtualKeyCodes.VK_BROWSER_FAVORITES, KeyHIDCodes.KEY_BROWSER_FAVORITES) // Browser Favorites
//          .put(VirtualKeyCodes.VK_BROWSER_FORWARD, KeyHIDCodes.KEY_BROWSER_FORWARD) // Browser Forward
//          .put(VirtualKeyCodes.VK_BROWSER_HOME, KeyHIDCodes.KEY_BROWSER_HOME) // Browser Home
//          .put(VirtualKeyCodes.VK_BROWSER_REFRESH, KeyHIDCodes.KEY_BROWSER_REFRESH) // Browser Refresh
//          .put(VirtualKeyCodes.VK_BROWSER_SEARCH, KeyHIDCodes.KEY_BROWSER_SEARCH) // Browser Search
//          .put(VirtualKeyCodes.VK_BROWSER_STOP, KeyHIDCodes.KEY_BROWSER_STOP) // Browser Stop
            .put(VirtualKeyCodes.VK_CAPITAL, KeyHIDCodes.KEY_CAPSLOCK) // Caps Lock
//          .put(VirtualKeyCodes.VK_CONVERT, KeyHIDCodes.KEY_CONVERT) // Convert
            .put(VirtualKeyCodes.VK_DELETE, KeyHIDCodes.KEY_DELETE) // Delete
            .put(VirtualKeyCodes.VK_DOWN, KeyHIDCodes.KEY_DOWN) // Arrow Down
            .put(VirtualKeyCodes.VK_END, KeyHIDCodes.KEY_END) // End
            .put(VirtualKeyCodes.VK_F1, KeyHIDCodes.KEY_F1) // F1
            .put(VirtualKeyCodes.VK_F10, KeyHIDCodes.KEY_F10) // F10
            .put(VirtualKeyCodes.VK_F11, KeyHIDCodes.KEY_F11) // F11
            .put(VirtualKeyCodes.VK_F12, KeyHIDCodes.KEY_F12) // F12
            .put(VirtualKeyCodes.VK_F13, KeyHIDCodes.KEY_F13) // F13
            .put(VirtualKeyCodes.VK_F14, KeyHIDCodes.KEY_F14) // F14
            .put(VirtualKeyCodes.VK_F15, KeyHIDCodes.KEY_F15) // F15
            .put(VirtualKeyCodes.VK_F16, KeyHIDCodes.KEY_F16) // F16
            .put(VirtualKeyCodes.VK_F17, KeyHIDCodes.KEY_F17) // F17
            .put(VirtualKeyCodes.VK_F18, KeyHIDCodes.KEY_F18) // F18
            .put(VirtualKeyCodes.VK_F19, KeyHIDCodes.KEY_F19) // F19
            .put(VirtualKeyCodes.VK_F2, KeyHIDCodes.KEY_F2) // F2
            .put(VirtualKeyCodes.VK_F20, KeyHIDCodes.KEY_F20) // F20
            .put(VirtualKeyCodes.VK_F21, KeyHIDCodes.KEY_F21) // F21
            .put(VirtualKeyCodes.VK_F22, KeyHIDCodes.KEY_F22) // F22
            .put(VirtualKeyCodes.VK_F23, KeyHIDCodes.KEY_F23) // F23
            .put(VirtualKeyCodes.VK_F24, KeyHIDCodes.KEY_F24) // F24
            .put(VirtualKeyCodes.VK_F3, KeyHIDCodes.KEY_F3) // F3
            .put(VirtualKeyCodes.VK_F4, KeyHIDCodes.KEY_F4) // F4
            .put(VirtualKeyCodes.VK_F5, KeyHIDCodes.KEY_F5) // F5
            .put(VirtualKeyCodes.VK_F6, KeyHIDCodes.KEY_F6) // F6
            .put(VirtualKeyCodes.VK_F7, KeyHIDCodes.KEY_F7) // F7
            .put(VirtualKeyCodes.VK_F8, KeyHIDCodes.KEY_F8) // F8
            .put(VirtualKeyCodes.VK_F9, KeyHIDCodes.KEY_F9) // F9
//          .put(VirtualKeyCodes.VK_FINAL, KeyHIDCodes.KEY_FINAL) // Final
            .put(VirtualKeyCodes.VK_HELP, KeyHIDCodes.KEY_HELP) // Help
            .put(VirtualKeyCodes.VK_HOME, KeyHIDCodes.KEY_HOME) // Home
//          .put(VirtualKeyCodes.VK_ICO_00, KeyHIDCodes.KEY_ICO_00) // Ico00 *
            .put(VirtualKeyCodes.VK_INSERT, KeyHIDCodes.KEY_INSERT) // Insert
//          .put(VirtualKeyCodes.VK_JUNJA, KeyHIDCodes.KEY_JUNJA) // Junja
//          .put(VirtualKeyCodes.VK_KANA, KeyHIDCodes.KEY_KANA) // Kana
//          .put(VirtualKeyCodes.VK_KANJI, KeyHIDCodes.KEY_KANJI) // Kanji
//          .put(VirtualKeyCodes.VK_LAUNCH_APP1, KeyHIDCodes.KEY_LAUNCH_APP1) // App1
//          .put(VirtualKeyCodes.VK_LAUNCH_APP2, KeyHIDCodes.KEY_LAUNCH_APP2) // App2
//          .put(VirtualKeyCodes.VK_LAUNCH_MAIL, KeyHIDCodes.KEY_LAUNCH_MAIL) // Mail
//          .put(VirtualKeyCodes.VK_LAUNCH_MEDIA_SELECT, KeyHIDCodes.KEY_LAUNCH_MEDIA_SELECT) // Media
//          .put(VirtualKeyCodes.VK_LBUTTON, KeyHIDCodes.KEY_LBUTTON) // Left Button **
            .put(VirtualKeyCodes.VK_LCONTROL, KeyHIDCodes.KEY_LEFTCTRL) // Left Ctrl
            .put(VirtualKeyCodes.VK_LEFT, KeyHIDCodes.KEY_LEFT) // Arrow Left
            .put(VirtualKeyCodes.VK_LMENU, KeyHIDCodes.KEY_LEFTALT) // Left Alt
            .put(VirtualKeyCodes.VK_LSHIFT, KeyHIDCodes.KEY_LEFTSHIFT) // Left Shift
            .put(VirtualKeyCodes.VK_LWIN, KeyHIDCodes.KEY_LEFTMETA) // Left Win
//          .put(VirtualKeyCodes.VK_MBUTTON, KeyHIDCodes.KEY_MBUTTON) // Middle Button **
            .put(VirtualKeyCodes.VK_MEDIA_NEXT_TRACK, KeyHIDCodes.KEY_MEDIA_NEXTSONG) // Next Track
            .put(VirtualKeyCodes.VK_MEDIA_PLAY_PAUSE, KeyHIDCodes.KEY_MEDIA_PLAYPAUSE) // Play / Pause
            .put(VirtualKeyCodes.VK_MEDIA_PREV_TRACK, KeyHIDCodes.KEY_MEDIA_PREVIOUSSONG) // Previous Track
            .put(VirtualKeyCodes.VK_MEDIA_STOP, KeyHIDCodes.KEY_MEDIA_STOP) // Stop
//          .put(VirtualKeyCodes.VK_MODECHANGE, KeyHIDCodes.KEY_MODECHANGE) // Mode Change
//          .put(VirtualKeyCodes.VK_NEXT, KeyHIDCodes.KEY_NEXT) // Page Down
//          .put(VirtualKeyCodes.VK_NONCONVERT, KeyHIDCodes.KEY_NONCONVERT) // Non Convert
            .put(VirtualKeyCodes.VK_NUMLOCK, KeyHIDCodes.KEY_NUMLOCK) // Num Lock
//          .put(VirtualKeyCodes.VK_OEM_FJ_JISHO, KeyHIDCodes.KEY_OEM_FJ_JISHO) // Jisho
//            .put(VirtualKeyCodes.VK_PAUSE, KeyHIDCodes.KEY_PAUSE) // Pause
//          .put(VirtualKeyCodes.VK_PRINT, KeyHIDCodes.KEY_PRINT) // Print
            .put(VirtualKeyCodes.VK_PRIOR, KeyHIDCodes.KEY_PAGEUP) // Page Up
//          .put(VirtualKeyCodes.VK_RBUTTON, KeyHIDCodes.KEY_RBUTTON) // Right Button **
            .put(VirtualKeyCodes.VK_RCONTROL, KeyHIDCodes.KEY_RIGHTCTRL) // Right Ctrl
            .put(VirtualKeyCodes.VK_RIGHT, KeyHIDCodes.KEY_RIGHT) // Arrow Right
            .put(VirtualKeyCodes.VK_RMENU, KeyHIDCodes.KEY_RIGHTALT) // Right Alt
            .put(VirtualKeyCodes.VK_RSHIFT, KeyHIDCodes.KEY_RIGHTSHIFT) // Right Shift
            .put(VirtualKeyCodes.VK_RWIN, KeyHIDCodes.KEY_RIGHTMETA) // Right Win
            .put(VirtualKeyCodes.VK_SCROLL, KeyHIDCodes.KEY_SCROLLLOCK) // Scrol Lock
            .put(VirtualKeyCodes.VK_SLEEP, KeyHIDCodes.KEY_MEDIA_SLEEP) // Sleep
            .put(VirtualKeyCodes.VK_SNAPSHOT, KeyHIDCodes.KEY_SYSRQ) // Print Screen
            .put(VirtualKeyCodes.VK_UP, KeyHIDCodes.KEY_UP) // Arrow Up
            .put(VirtualKeyCodes.VK_VOLUME_DOWN, KeyHIDCodes.KEY_VOLUMEDOWN) // Volume Down
            .put(VirtualKeyCodes.VK_VOLUME_MUTE, KeyHIDCodes.KEY_MUTE) // Volume Mute
            .put(VirtualKeyCodes.VK_VOLUME_UP, KeyHIDCodes.KEY_VOLUMEUP) // Volume Up
//          .put(VirtualKeyCodes.VK_XBUTTON1, KeyHIDCodes.KEY_XBUTTON1) // X Button 1 **
//          .put(VirtualKeyCodes.VK_XBUTTON2, KeyHIDCodes.KEY_XBUTTON2) // X Button 2 **
            .build();

    public static BiMap<String, Integer> ELITE_HID = new ImmutableBiMap.Builder<String, Integer>()
            .put("Key_Escape", KeyHIDCodes.KEY_ESC)
            .put("Key_F1", KeyHIDCodes.KEY_F1)
            .put("Key_F2", KeyHIDCodes.KEY_F2)
            .put("Key_F3", KeyHIDCodes.KEY_F3)
            .put("Key_F4", KeyHIDCodes.KEY_F4)
            .put("Key_F5", KeyHIDCodes.KEY_F5)
            .put("Key_F6", KeyHIDCodes.KEY_F6)
            .put("Key_F7", KeyHIDCodes.KEY_F7)
            .put("Key_F8", KeyHIDCodes.KEY_F8)
            .put("Key_F9", KeyHIDCodes.KEY_F9)
            .put("Key_F10", KeyHIDCodes.KEY_F10)
            .put("Key_F11", KeyHIDCodes.KEY_F11)
            .put("Key_F12", KeyHIDCodes.KEY_F12)
            .put("Key_PrintScreen", KeyHIDCodes.KEY_SYSRQ)
            .put("Key_ScrollLock", KeyHIDCodes.KEY_SCROLLLOCK)
            .put("Key_PauseBreak", KeyHIDCodes.KEY_PAUSE)
            .put("Key_Grave", KeyHIDCodes.KEY_GRAVE)
            .put("Key_1", KeyHIDCodes.KEY_1)
            .put("Key_2", KeyHIDCodes.KEY_2)
            .put("Key_3", KeyHIDCodes.KEY_3)
            .put("Key_4", KeyHIDCodes.KEY_4)
            .put("Key_5", KeyHIDCodes.KEY_5)
            .put("Key_6", KeyHIDCodes.KEY_6)
            .put("Key_7", KeyHIDCodes.KEY_7)
            .put("Key_8", KeyHIDCodes.KEY_8)
            .put("Key_9", KeyHIDCodes.KEY_9)
            .put("Key_0", KeyHIDCodes.KEY_0)
            .put("Key_Minus", KeyHIDCodes.KEY_MINUS)
            .put("Key_Equals", KeyHIDCodes.KEY_EQUAL)
            .put("Key_Backspace", KeyHIDCodes.KEY_BACKSPACE)
            .put("Key_Insert", KeyHIDCodes.KEY_INSERT)
            .put("Key_Home", KeyHIDCodes.KEY_HOME)
            .put("Key_PageUp", KeyHIDCodes.KEY_PAGEUP)
            .put("Key_Numpad_Lock", KeyHIDCodes.KEY_NUMLOCK)
            .put("Key_Numpad_Divide", KeyHIDCodes.KEY_KPSLASH)
            .put("Key_Numpad_Multiply", KeyHIDCodes.KEY_KPASTERISK)
            .put("Key_Numpad_Subtract", KeyHIDCodes.KEY_KPMINUS)
            .put("Key_Tab", KeyHIDCodes.KEY_TAB)
            .put("Key_Q", KeyHIDCodes.KEY_Q)
            .put("Key_W", KeyHIDCodes.KEY_W)
            .put("Key_E", KeyHIDCodes.KEY_E)
            .put("Key_R", KeyHIDCodes.KEY_R)
            .put("Key_T", KeyHIDCodes.KEY_T)
            .put("Key_Y", KeyHIDCodes.KEY_Y)
            .put("Key_U", KeyHIDCodes.KEY_U)
            .put("Key_I", KeyHIDCodes.KEY_I)
            .put("Key_O", KeyHIDCodes.KEY_O)
            .put("Key_P", KeyHIDCodes.KEY_P)
            .put("Key_LeftBracket", KeyHIDCodes.KEY_LEFTBRACE)
            .put("Key_RightBracket", KeyHIDCodes.KEY_RIGHTBRACE)
            .put("Key_BackSlash", KeyHIDCodes.KEY_HASHTILDE)
            .put("Key_Delete", KeyHIDCodes.KEY_DELETE)
            .put("Key_End", KeyHIDCodes.KEY_END)
            .put("Key_PageDown", KeyHIDCodes.KEY_PAGEDOWN)
            .put("Key_Numpad_7", KeyHIDCodes.KEY_KP7)
            .put("Key_Numpad_8", KeyHIDCodes.KEY_KP8)
            .put("Key_Numpad_9", KeyHIDCodes.KEY_KP9)
            .put("Key_Numpad_Add", KeyHIDCodes.KEY_KPPLUS)
            .put("Key_CapsLock", KeyHIDCodes.KEY_CAPSLOCK)
            .put("Key_A", KeyHIDCodes.KEY_A)
            .put("Key_S", KeyHIDCodes.KEY_S)
            .put("Key_D", KeyHIDCodes.KEY_D)
            .put("Key_F", KeyHIDCodes.KEY_F)
            .put("Key_G", KeyHIDCodes.KEY_G)
            .put("Key_H", KeyHIDCodes.KEY_H)
            .put("Key_J", KeyHIDCodes.KEY_J)
            .put("Key_K", KeyHIDCodes.KEY_K)
            .put("Key_L", KeyHIDCodes.KEY_L)
            .put("Key_SemiColon", KeyHIDCodes.KEY_SEMICOLON)
            .put("Key_Apostrophe", KeyHIDCodes.KEY_APOSTROPHE)
            .put("Key_Enter", KeyHIDCodes.KEY_ENTER)
            .put("Key_Numpad_4", KeyHIDCodes.KEY_KP4)
            .put("Key_Numpad_5", KeyHIDCodes.KEY_KP5)
            .put("Key_Numpad_6", KeyHIDCodes.KEY_KP6)
            .put("Key_LeftShift", KeyHIDCodes.KEY_LEFTSHIFT)
            .put("Key_Z", KeyHIDCodes.KEY_Z)
            .put("Key_X", KeyHIDCodes.KEY_X)
            .put("Key_C", KeyHIDCodes.KEY_C)
            .put("Key_V", KeyHIDCodes.KEY_V)
            .put("Key_B", KeyHIDCodes.KEY_B)
            .put("Key_N", KeyHIDCodes.KEY_N)
            .put("Key_M", KeyHIDCodes.KEY_M)
            .put("Key_Comma", KeyHIDCodes.KEY_COMMA)
            .put("Key_Period", KeyHIDCodes.KEY_DOT)
            .put("Key_Slash", KeyHIDCodes.KEY_SLASH)
            .put("Key_RightShift", KeyHIDCodes.KEY_RIGHTSHIFT)
            .put("Key_UpArrow", KeyHIDCodes.KEY_UP)
            .put("Key_Numpad_1", KeyHIDCodes.KEY_KP1)
            .put("Key_Numpad_2", KeyHIDCodes.KEY_KP2)
            .put("Key_Numpad_3", KeyHIDCodes.KEY_KP3)
            .put("Key_Numpad_Enter", KeyHIDCodes.KEY_KPENTER)
            .put("Key_LeftControl", KeyHIDCodes.KEY_LEFTCTRL)
            .put("Key_LeftWindows", KeyHIDCodes.KEY_LEFTMETA)
            .put("Key_LeftAlt", KeyHIDCodes.KEY_LEFTALT)
            .put("Key_Space", KeyHIDCodes.KEY_SPACE)
            .put("Key_RightAlt", KeyHIDCodes.KEY_RIGHTALT)
            .put("Key_RightWindows", KeyHIDCodes.KEY_RIGHTMETA)
            .put("Key_Application_select", KeyHIDCodes.KEY_COMPOSE)
            .put("Key_RightControl", KeyHIDCodes.KEY_RIGHTCTRL)
            .put("Key_LeftArrow", KeyHIDCodes.KEY_LEFT)
            .put("Key_DownArrow", KeyHIDCodes.KEY_DOWN)
            .put("Key_RightArrow", KeyHIDCodes.KEY_RIGHT)
            .put("Key_Numpad_0", KeyHIDCodes.KEY_KP0)
            .put("Key_Numpad_Decimal", KeyHIDCodes.KEY_KPDOT)
            .build();

    @Nullable
    public static String getEliteKeyFromVirtual(Integer virtualKeyCode) {
        Integer hidCode = VIRTUAL_HID.get(virtualKeyCode);
        if(hidCode != null) {
            return ELITE_HID.inverse().get(hidCode);
        }

        return null;
    }
}
