function Preview() {
    var e = this;
    this.m_szXmlStr = '',
    this.m_bChannelPlay = [
    ],
    this.m_bChannelRecord = [
    ],
    this.m_iWndChannel = [
    ],
    this.m_iChannelWindow = [
    ],
    this.m_bSound = [
    ],
    this.m_iChannelStream = [
    ],
    this.m_bEnableEZoom = [
    ],
    this.m_bEnable3DZoom = [
    ],
    this.m_bPTZAuto = [
    ],
    this.m_iCurWnd = 0,
    this.m_iCurWndChannel = - 1,
    this.m_iCurWndNum = 1,
    this.m_bAllPlay = !1,
    this.m_bAllRecord = !1;
    for (var t = 0; 256 > t; t++) this.m_bSound[t] = 0,
    this.m_bChannelPlay[t] = 0,
    this.m_bChannelRecord[t] = 0,
    this.m_iWndChannel[t] = - 1,
    this.m_iChannelWindow[t] = - 1,
    this.m_bEnableEZoom[t] = !1,
    this.m_bEnable3DZoom[t] = !1,
    this.m_bPTZAuto[t] = !1,
    this.m_iChannelStream[t] = 0;
    this.m_bIsDiskFreeSpaceEnough = !0,
    this.m_iTotalPageNum = 0,
    this.m_iCurrentPageNum = 0,
    this.m_bIsSupportPage = !1,
    this.m_oSliderPtzSpd = null,
    this.m_oSliderVolume = null,
    this.m_oSliderBright = null,
    this.m_oSliderContrast = null,
    this.m_oSliderSaturation = null,
    this.m_oSliderHue = null,
    this.m_oSliderSharpness = null,
    this.m_oSliderNoiseReduce = null,
    this.m_iBright = 0,
    this.m_iContrast = 0,
    this.m_iSaturation = 0,
    this.m_iHue = 0,
    this.m_iSharpness = 0,
    this.m_iNoiseReduce = 0,
    this.m_iBrightMin = 0,
    this.m_iBrightMax = 255,
    this.m_iContrastMin = 0,
    this.m_iContrastMax = 255,
    this.m_iSaturationMin = 0,
    this.m_iSaturationMax = 255,
    this.m_iHueMin = 0,
    this.m_iHueMax = 255,
    this.m_iSharpnessMin = 0,
    this.m_iSharpnessMax = 3,
    this.m_iNoiseReduceMin = 0,
    this.m_iNoiseReduceMax = 5,
    this.m_iBrightDefault = 128,
    this.m_iContrastDefault = 129,
    this.m_iSaturationDefault = 132,
    this.m_iHueDefault = 153,
    this.m_iSharpnessDefault = 0,
    this.m_iNoiseReduceDefault = 2,
    this.m_bSupportScene = !1,
    this.m_iSceneParamDef = [
    ];
    for (var t = 0; 4 > t; t++) {
        this.m_iSceneParamDef[t] = [
        ];
        for (var a = 0; 6 > a; a++) this.m_iSceneParamDef[t][a] = 0
    }
    this.m_bVideoExpand = !1,
    this.m_oSelectPreset = null,
    this.m_iSelectPatrolPreset = - 1,
    this.m_oSelectPattern = null,
    this.m_iOperateMode = 0,
    this.m_oOperated = null,
    this.m_iProtocolType = 0,
    this.m_lxdPreview = null,
    this.m_iWndArrangePartTimeOut = - 1,
    this.m_iEnLargeZeroChannel = - 1,
    this.m_iPTZPatrolNum = 0,
    this.m_szSecretKey = '',
    this.m_bNoStreamSecret = !1,
    this.InitPreview = function () {
		
        if ($.cookie('page', null), g_oCommon.changAuthInfo(g_oWebSession.getItem('userInfo')), null == g_oCommon.m_szUserPwdValue) return window.parent.location.href = 'login.asp',
        void 0;
        window.parent.$('#curruser').text(g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0]),
        //window.parent.g_oMain.changeMenu(1),
        g_oCommon.getTreeTable(),
        g_oCommon.getRTSPPort(),
        //e.InitTree(),
        $('#presetParam').height($('#content_right').height() - $('#PTZ').height() - $('#VideoEffect').height() - 35),
        $('#PresetArea').height($('#presetParam').height()),
        $('#CruiseArea').height($('#presetParam').height()),
        $('#PatrolPresetList').height($('#CruiseArea').height() - 26),
        $('#TrackArea').height($('#presetParam').height()),
        $('#videoParam').height($('#Preset').height() - 20),
        //e.InitSlider(),
        //e.InitPreset(),
        //e.InitPattern(),
        //e.InitPresetList(),
        g_oCommon.getCapbilities(),
        e.GetPatrolsCab(),
        e.GetPatternCab();
        //e.changeLanguage(parent.translator.szCurLanguage);
        var t = 0;
        t = Math.ceil(Math.sqrt(g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum)),
        0 == t && (t = 1),
        t > 4 && (t = 4);
        for (var a = 0; t > a && 4 > a; a++) $('#wnd' + (a + 1) * (a + 1)).show();
        e.m_iCurWndNum = t * t;
        for (var a = 1; 5 > a; a++) a != t ? $('#wnd' + a * a).attr('src', '../images/public/Arrange/Wnd' + a * a + '.png')  : $('#wnd' + a * a).attr('src', '../images/public/Arrange/PushWnd' + a * a + '.png');
        var o = g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum;
        e.m_iTotalPageNum = 0 == o % e.m_iCurWndNum ? o / e.m_iCurWndNum : parseInt(o / e.m_iCurWndNum, 10) + 1,
        e.m_iCurWndNum >= o ? (e.m_bIsSupportPage = !1, $('#frontpage').attr('src', '../images/public/VideoBottom/disabled/Preview_DisFrontPage.png'), $('#nextpage').attr('src', '../images/public/VideoBottom/disabled/Preview_DisNextPage.png'))  : (e.m_bIsSupportPage = !0, $('#frontpage').attr('src', '../images/public/VideoBottom/normal/Preview_FrontPage.png'), $('#nextpage').attr('src', '../images/public/VideoBottom/normal/Preview_NextPage.png')),
        $(window.parent.document.getElementById('footer')).append('<div id=\'WndArrangePart\' style=\'display:none; height:31px; position:absolute; z-index:10; left:218px; bottom:0px;\'>' + $('#WndArrangePart').html() + '</div>'),
        $(window.parent.document.getElementById('WndArrangePart')).bind({
            mouseleave: function () {
                $(this).hide()
            }
        });
        for (var n = 0; 4 > n; n++) {
            var r = window.parent.document.getElementById('wnd' + (n + 1) * (n + 1));
            $(r).bind('mouseout', {
                iWndType: n + 1
            }, function (t) {
                var a = t.data.iWndType;
                e.MouseOutWnd(a)
            }),
            $(r).bind('mouseover', {
                iWndType: n + 1
            }, function (e) {
                var t = e.data.iWndType;
                $(this).attr('src', '../images/public/Arrange/SelWnd' + t * t + '.png')
            }),
            $(r).bind('click', {
                iWndType: n + 1
            }, function (t) {
                var a = t.data.iWndType;
                e.ChangeWnd(a)
            })
        }
        if ($('#ArrangeWnd').bind({
            mouseenter: function () {
                $(window.parent.document.getElementById('WndArrangePart')).show(),
                - 1 != e.m_iWndArrangePartTimeOut && clearTimeout(e.m_iWndArrangePartTimeOut)
            },
            mouseleave: function () {
                e.m_iWndArrangePartTimeOut = setTimeout(function () {
                    $(window.parent.document.getElementById('WndArrangePart')).hide()
                }, 500)
            }
        }), $(window.parent.document.getElementById('WndArrangePart')).bind({
            mouseenter: function () {
                - 1 != e.m_iWndArrangePartTimeOut && clearTimeout(e.m_iWndArrangePartTimeOut)
            }
        }), $('#WndArrangePart').remove(), $(window.parent.document.getElementById('footer')).append('<div id=\'volumeDiv\' style=\'display:none; position:absolute; height:23px; width:73px; z-index:10; right:200px; bottom:0px;\' align=\'left\'></div>'), $(window.parent.document.getElementById('volumeDiv')).bind({
            mouseleave: function () {
                $(this).hide()
            }
        }), window.parent.m_oSliderVolume = new neverModules.modules.slider({
            targetId: 'volumeDiv',
            sliderCss: 'VolumeSlider',
            barCss: 'VolumeBar',
            parent: window.parent,
            min: 0,
            max: 100
        }),$(window).bind('resize', function () {
            var e = $(window).height() - $('#PTZ').height() - 63;
            $('#PatrolPresetList').height(e - 26),
            $('#CruiseArea').height(e),
            $('#PresetArea').height(e),
            $('#TrackArea').height(e),
            $('#presetParam').height($(window).height() - $('#PTZ').height() - 63),
            $('#videoParam').height(e + 15),
            $('#PreviewActiveX').height(document.body.clientHeight - 34)
        }), $('#PatrolTime').bind('keyup', function () {
            parseInt($(this).val(), 10) >= 0 && 30 >= parseInt($(this).val(), 10) ? $(this).val(parseInt($(this).val(), 10))  : parseInt($(this).val(), 10) > 30 ? $(this).val('30')  : $(this).val('1')
        }), $('#PatrolSpeed').bind('keyup', function () {
            parseInt($(this).val(), 10) >= 1 && 40 >= parseInt($(this).val(), 10) ? $(this).val(parseInt($(this).val(), 10))  : parseInt($(this).val(), 10) > 40 ? $(this).val('40')  : $(this).val('1')
        })) {
            g_oCommon.m_PreviewOCX = document.getElementById('PreviewActiveX'),
            g_oCommon.compareFileVersion() || g_oCommon.updateTips();
            var i = '';
            try {
                i = g_oCommon.m_PreviewOCX.HWP_GetLocalConfig()
            } catch (s) {
                i = g_oCommon.m_PreviewOCX.GetLocalConfig()
            }
            var m = g_oCommon.parseXmlFromStr(i);
            e.m_szSecretKey = '' != $(m).find('SecretKey').eq(0).text() ? $(m).find('SecretKey').eq(0).text()  : '';
            var l = parseInt($(m).find('StreamType').eq(0).text());
            e.m_iProtocolType = parseInt($(m).find('ProtocolType').eq(0).text()),
            2 != e.m_iProtocolType && 0 != e.m_iProtocolType && ($(m).find('ProtocolType').eq(0).text('0'), g_oCommon.m_PreviewOCX.HWP_SetLocalConfig(g_oCommon.xmlToStr(m)), e.m_iProtocolType = 0);
            for (var a = 0; 256 > a; a++) e.m_iChannelStream[a] = l;
            1 === l ? ($('#sub_menu').find('.stream').attr('src', '../images/public/ICON/sub_stream.png'), $('#sub_menu').find('.stream').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'streamTypeOpt2')))  : $('#sub_menu').find('.stream').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'streamTypeOpt1')),
            g_oCommon.isSupportShttp();
            var d = document.URL;
            if ( - 1 == d.indexOf('&')) '1' === $(m).find('RealPlayAll').eq(0).text() && e.RealPlayAll(),
            'ja' == parent.translator.szCurLanguage && g_oCommon.changeJaFont();
             else if ( - 1 != d.indexOf('slice=')) {
                if ( - 1 != d.indexOf('&open=')) for (var g = Math.ceil(Math.sqrt(parseInt(d.substring(d.indexOf('slice=') + 6, d.indexOf('&open=')), 10))), p = d.substring(d.indexOf('&open={') + 7, d.indexOf('}')), a = 0; p.split(',').length > a; a++) setTimeout('g_oPreviewInstance.StartRealPlay(' + p.split(',') [a] + ',' + a + ')', 100);
                 else var g = Math.ceil(Math.sqrt(parseInt(d.substring(d.indexOf('slice=') + 6, d.length), 10)));
                e.ChangeWnd(g)
            }
        }
    },
    this.InitTree = function () {
        $('#DeviceName').html(window.parent.g_oMain.m_szDeviceName),
        $('#DeviceName').attr('title', window.parent.g_oMain.m_szDeviceName),
        g_oCommon.getChannelInfo(),
        g_oCommon.getDigChannelInfo(),
        g_oCommon.getZeroChannelInfo();
        for (var e = $('#sub_menu').html(), t = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;', a = 0; g_oCommon.m_iAnalogChannelNum > a; a++) {
            var o = '';
            try {
                o = g_oCommon.m_oXmlDoc.documentElement.getElementsByTagName('VideoInputChannel') [a].getElementsByTagName('name') [0].childNodes[0].nodeValue
            } catch (n) {
                o = ''
            }
            '' == o && (o = 9 > a ? 'Camera 0' + (a + 1)  : 'Camera ' + (a + 1)),
            o = o.replace(/\s/g, '&nbsp;'),
            e += '<div class=\'ellipsis\' id=\'Camera' + (a + 1) + '\' style=\'height:25px; width:184px; white-space:nowrap;\'>' + t + '<img src=\'../images/public/ICON/main_stream.png\' align=\'absmiddle\' class=\'stream\' id=\'Stream' + (a + 1) + 'Img\' onclick=\'g_oPreviewInstance.switchStream(' + (a + 1) + ')\' style=\'cursor:pointer\'>&nbsp;' + '<img src=\'../images/public/ICON/Camera_1.png\' align=\'absmiddle\' id=\'Camera' + (a + 1) + 'Img\' onClick=\'g_oPreviewInstance.StartRealPlay(' + (a + 1) + ')\'  style=\'cursor:pointer\'>&nbsp;<img src=\'../images/public/ICON/record.png\' align=\'absmiddle\' id=\'Record' + (a + 1) + 'Img\' onClick=\'g_oPreviewInstance.StartRecord(' + (a + 1) + ',"")\' style=\'cursor:pointer;\'><span style=\'cursor:pointer;color:#000000;-moz-user-select:none;\' id=\'Selected' + (a + 1) + 'color\' onClick=\'g_oPreviewInstance.SetFontColor(' + (a + 1) + ')\' onDblClick=\'g_oPreviewInstance.StartRealPlay(' + (a + 1) + ')\' onselectstart=\'return false;\' title=\'' + o + '\'>&nbsp;' + o + '</span></div>'
        }
        for (var r = 0; g_oCommon.m_iDigitalChannelNum > r; r++) {
            var i = '';
            try {
                i = g_oCommon.m_oDigXmlDoc.documentElement.getElementsByTagName('InputProxyChannel') [r].getElementsByTagName('name') [0].childNodes[0].nodeValue
            } catch (n) {
                i = ''
            }
            '' == i && (i = 9 > r ? 'IPCamera 0' + (r + 1)  : 'IPCamera ' + (r + 1)),
            i = i.replace(/\s/g, '&nbsp;'),
            e += '<div class=\'ellipsis\' id=\'Camera' + (r + g_oCommon.m_iAnalogChannelNum + 1) + '\' style=\'height:25px; width:184px; white-space:nowrap;\'>' + t + '<img src=\'../images/public/ICON/main_stream.png\' align=\'absmiddle\' class=\'stream\' id=\'Stream' + (r + g_oCommon.m_iAnalogChannelNum + 1) + 'Img\' onClick=\'g_oPreviewInstance.switchStream(' + (r + g_oCommon.m_iAnalogChannelNum + 1) + ')\' style=\'cursor:pointer\'>&nbsp;' + '<img src=\'../images/public/ICON/Camera_1.png\' align=\'absmiddle\' id=\'Camera' + (r + g_oCommon.m_iAnalogChannelNum + 1) + 'Img\' onClick=\'g_oPreviewInstance.StartRealPlay(' + (r + g_oCommon.m_iAnalogChannelNum + 1) + ')\'  style=\'cursor:pointer\'>&nbsp;<img src=\'../images/public/ICON/record.png\' align=\'absmiddle\' id=\'Record' + (r + g_oCommon.m_iAnalogChannelNum + 1) + 'Img\' onClick=\'g_oPreviewInstance.StartRecord(' + (r + g_oCommon.m_iAnalogChannelNum + 1) + ',"")\' style=\'cursor:pointer\'><span style=\'cursor:pointer;color:#000000;-moz-user-select:none;\' id=\'Selected' + (r + g_oCommon.m_iAnalogChannelNum + 1) + 'color\' onClick=\'g_oPreviewInstance.SetFontColor(' + (r + g_oCommon.m_iAnalogChannelNum + 1) + ')\' onDblClick=\'g_oPreviewInstance.StartRealPlay(' + (r + g_oCommon.m_iAnalogChannelNum + 1) + ')\' href=\'#\' class=\'aurl\' onselectstart=\'return false;\' title=\'' + i + '\'>&nbsp;' + i + '</span></div>'
        }
        for (var s = 0; g_oCommon.m_iZeroChanNum > s; s++) {
            var m = '';
            try {
                m = g_oCommon.m_oZeroXmlDoc.documentElement.getElementsByTagName('ZeroVideoChannel') [s].getElementsByTagName('name') [0].childNodes[0].nodeValue
            } catch (n) {
                m = ''
            }
            '' == m && (m = 9 > s ? 'ZeroChannel 0' + (s + 1)  : 'ZeroChannel ' + (s + 1)),
            m = m.replace(/\s/g, '&nbsp;'),
            e += '<div class=\'ellipsis\' id=\'Camera' + (s + g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + 1) + '\' style=\'padding-left:20px;height:25px; width:184px; white-space:nowrap;\'>' + t + '<img src=\'../images/public/ICON/Camera_1.png\' align=\'absmiddle\' id=\'Camera' + (s + g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + 1) + 'Img\' onClick=\'g_oPreviewInstance.StartRealPlay(' + (s + g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + 1) + ')\'  style=\'cursor:pointer\'>&nbsp;<img src=\'../images/public/ICON/record.png\' align=\'absmiddle\' id=\'Record' + (s + g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + 1) + 'Img\' onClick=\'g_oPreviewInstance.StartRecord(' + (s + g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + 1) + ',"")\' style=\'cursor:pointer\'><span style=\'cursor:pointer;color:#000000;-moz-user-select:none;\' id=\'Selected' + (s + g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + 1) + 'color\' onClick=\'g_oPreviewInstance.SetFontColor(' + (s + g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + 1) + ')\' onDblClick=\'g_oPreviewInstance.StartRealPlay(' + (s + g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + 1) + ')\' href=\'#\' class=\'aurl\' onselectstart=\'return false;\' title=\'' + m + '\'>&nbsp;' + m + '</span></div>'
        }
        $('#sub_menu').html(e)
    },
    this.InitPatrolLan = function () {
        $('#selectPatrol').empty();
        for (var t = parent.translator.translateNode(e.m_lxdPreview, 'laTrack'), a = '', o = 0; e.m_iPTZPatrolNum > o; o++) a += '<option value=\'' + (o + 1) + '\'>' + t + ' 0' + (o + 1) + '</option>';
        $(a).appendTo('#selectPatrol')
    },
    this.InitPresetList = function () {
        $('#SelectPreset').empty();
        for (var t = parent.translator.translateNode(e.m_lxdPreview, 'laPreset'), a = '', o = 0; 256 > o; o++) a += '<option value=\'' + (o + 1) + '\'>' + t + ' ' + (o + 1) + '</option>';
        $(a).appendTo('#SelectPreset')
    },
    this.InitPresetListLan = function () {
        var t = parent.translator.translateNode(e.m_lxdPreview, 'laPreset');
        $('#SelectPreset').find('option').each(function () {
            var e = $(this).html();
            $(this).html(t + ' ' + e.split(' ') [1])
        })
    },
    this.StartRealPlay = function (base64code,m) {
		g_oCommon.m_PreviewOCX = document.getElementById('PreviewActiveX');
		g_oCommon.m_PreviewOCX.HWP_Play(m, base64code, 0, '', '');
    },
    this.StopRealPlay = function (t) {
        if (1 == e.m_bChannelRecord[t - 1] && e.StopRecord(t), 0 != g_oCommon.m_PreviewOCX.HWP_Stop(e.m_iChannelWindow[t - 1])) return alert(parent.translator.translateNode(e.m_lxdPreview, 'previewfailed')),
        void 0;
        document.getElementById('Camera' + t + 'Img').src = '../images/public/ICON/Camera_1.png',
        document.getElementById('Camera' + t + 'Img').title = parent.translator.translateNode(e.m_lxdPreview, 'jsPreview'),
        e.m_bChannelPlay[t - 1] = 0,
        e.m_bChannelRecord[t - 1] = 0,
        e.m_iWndChannel[e.m_iChannelWindow[t - 1]] = - 1;
        try {
            g_oCommon.m_PreviewOCX.HWP_DisableZoom(e.m_iChannelWindow[t - 1])
        } catch (a) {
        }
        e.m_bEnableEZoom[e.m_iChannelWindow[t - 1]] = !1,
        e.m_bEnable3DZoom[e.m_iChannelWindow[t - 1]] = !1,
        $('#Ezoom').prop('src', '../images/public/VideoBottom/disabled/DisZoom.png'),
        $('#Ezoom').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'laEnableZoom')),
        $('#zoom3D').prop('src', '../images/public/VideoBottom/disabled/Dis3D.png'),
        $('#zoom3D').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'Start3DZoom')),
        e.m_iChannelWindow[t - 1] = - 1,
        e.m_bSound[t - 1] && ($('#opensound').unbind(), $('#opensound').bind({
            mouseover: function () {
                $(this).attr('src', '../images/public/VideoBottom/select/SelCloseSound.png')
            },
            mouseout: function () {
                $(this).attr('src', '../images/public/VideoBottom/normal/CloseSound.png')
            }
        }), $('#opensound').attr('src', '../images/public/VideoBottom/normal/CloseSound.png'), $('#opensound').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'opensound')), $('#Ezoom').attr('src', '../images/public/VideoBottom/disabled/DisZoom.png'), $('#zoom3D').attr('src', '../images/public/VideoBottom/disabled/Dis3D.png'), $(window.parent.document.getElementById('volumeDiv')).hide()),
        e.m_bSound[t - 1] = 0,
        t == e.m_iCurWndChannel && (e.m_iCurWndChannel = - 1, e.ResetVideoParam());
        for (var o = 0; g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum > o; o++) if (0 != e.m_bChannelPlay[o]) return;
        e.m_bAllPlay = !1,
        $('#play').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'paStartAllView')),
        $('#play').attr('src', '../images/public/VideoBottom/normal/RealPlayAll.png')
    },
    this.StartRecord = function (t) {
        var a = document.getElementById('Record' + t + 'Img');
        if (0 == e.m_bChannelRecord[t - 1]) {
            if (1 == e.m_bChannelPlay[t - 1]) {
                var o = new Date,
                n = '',
                r = '';
                r = 0 > g_oCommon.m_szHostName.indexOf('[') ? g_oCommon.m_szHostName : g_oCommon.m_szHostName.substring(1, g_oCommon.m_szHostName.length - 1).replaceAll(':', '.'),
                n = 9 >= t ? r + '_0' + t + '_' + o.Format('yyyyMMddhhmmssS')  : r + '_' + t + '_' + o.Format('yyyyMMddhhmmssS');
                var i = g_oCommon.m_PreviewOCX.HWP_StartSave(e.m_iChannelWindow[t - 1], n);
                if (0 == i) a.src = '../images/public/ICON/recording.png',
                a.title = parent.translator.translateNode(e.m_lxdPreview, 'stoprecord'),
                e.m_bChannelRecord[t - 1] = 1,
                e.m_bAllRecord = !0,
                $('#record').attr('src', '../images/public/VideoBottom/normal/StopRecordAll.png'),
                $('#record').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'stopAllRecord')),
                e.m_bIsDiskFreeSpaceEnough = !0;
                 else if ( - 1 == i) {
                    var s = g_oCommon.m_PreviewOCX.HWP_GetLastError();
                    10 == s || 9 == s ? alert(parent.translator.translateNode(e.m_lxdPreview, 'jsCreateFileFailed'))  : alert(parent.translator.translateNode(e.m_lxdPreview, 'recordfailed'))
                } else - 2 == i ? (e.m_bIsDiskFreeSpaceEnough = !1, alert(parent.translator.translateNode(e.m_lxdPreview, 'FreeSpaceTips')))  : - 3 == i && alert(parent.translator.translateNode(e.m_lxdPreview, 'jsCreateFileFailed'))
            }
        } else e.StopRecord(t)
    },
    this.StopRecord = function (t) {
        var a = parent.translator.translateNode(e.m_lxdPreview, 'jsRecord'),
        o = g_oCommon.m_PreviewOCX.HWP_StopSave(e.m_iChannelWindow[t - 1]);
        0 == o && (document.getElementById('Record' + t + 'Img').src = '../images/public/ICON/record.png', document.getElementById('Record' + t + 'Img').title = a, e.m_bChannelRecord[t - 1] = 0, window.parent.g_oMain.showTipsDiv('', parent.translator.translateNode(e.m_lxdPreview, 'jsRecordSucc')));
        for (var n = 0; g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum > n; n++) if (0 != e.m_bChannelRecord[n]) return;
        e.m_bAllRecord = !1,
        $('#record').attr('src', '../images/public/VideoBottom/normal/RecordAll.png'),
        $('#record').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'paStartAllRecord'))
    },
    this.RealPlayAll = function () {
        if (g_oPreviewInstance.m_bNoStreamSecret = !1, e.m_bAllPlay) e.StopRealPlayAll();
         else {
            var t = 0;
            t = g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum <= e.m_iCurWndNum ? g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum : e.m_iCurWndNum;
            for (var a = 0; t > a; a++) e.StartRealPlay(a + 1, a);
            e.m_iCurWndChannel = e.m_iWndChannel[e.m_iCurWnd],
            - 1 != e.m_iCurWndChannel ? (e.GetVideoParam(e.m_iCurWndChannel), e.SetFontColor(e.m_iCurWndChannel))  : (e.m_iCurWndChannel = - 1, e.ResetVideoParam())
        }
        for (var a = 0; 256 > a; a++) e.m_bSound[a] = 0;
        $('#opensound').unbind(),
        $('#opensound').bind({
            mouseover: function () {
                $(this).attr('src', '../images/public/VideoBottom/select/SelCloseSound.png')
            },
            mouseout: function () {
                $(this).attr('src', '../images/public/VideoBottom/normal/CloseSound.png')
            }
        }),
        $('#opensound').attr('src', '../images/public/VideoBottom/normal/CloseSound.png'),
        $('#opensound').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'opensound')),
        $(window.parent.document.getElementById('volumeDiv')).hide()
    },
    this.StopRealPlayAll = function () {
        for (var t = 0; g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum > t; t++) if (1 == e.m_bChannelPlay[t]) {
            var a = t + 1;
            if (1 == e.m_bChannelRecord[t]) {
                var o = parent.translator.translateNode(e.m_lxdPreview, 'jsRecord'),
                n = g_oCommon.m_PreviewOCX.HWP_StopSave(e.m_iChannelWindow[t]);
                0 == n && (document.getElementById('Record' + a + 'Img').src = '../images/public/ICON/record.png', document.getElementById('Record' + a + 'Img').title = o, window.parent.g_oMain.showTipsDiv('', parent.translator.translateNode(e.m_lxdPreview, 'jsRecordSucc')))
            }
            if (0 != g_oCommon.m_PreviewOCX.HWP_Stop(e.m_iChannelWindow[t])) continue;
            try {
                g_oCommon.m_PreviewOCX.HWP_DisableZoom(e.m_iChannelWindow[t])
            } catch (r) {
            }
            e.m_bEnableEZoom[e.m_iChannelWindow[t]] = !1,
            e.m_bEnable3DZoom[e.m_iChannelWindow[t]] = !1,
            document.getElementById('Camera' + a + 'Img').src = '../images/public/ICON/Camera_1.png',
            document.getElementById('Camera' + a + 'Img').title = parent.translator.translateNode(e.m_lxdPreview, 'jsPreview'),
            e.m_bChannelPlay[t] = 0,
            e.m_bChannelRecord[t] = 0,
            e.m_iWndChannel[e.m_iChannelWindow[t]] = - 1,
            e.m_iChannelWindow[t] = - 1,
            e.m_bSound[t] && ($('#opensound').unbind(), $('#opensound').bind({
                mouseover: function () {
                    $(this).attr('src', '../images/public/VideoBottom/select/SelCloseSound.png')
                },
                mouseout: function () {
                    $(this).attr('src', '../images/public/VideoBottom/normal/CloseSound.png')
                }
            }), $('#opensound').attr('src', '../images/public/VideoBottom/normal/CloseSound.png'), $('#opensound').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'opensound')), $(window.parent.document.getElementById('volumeDiv')).hide()),
            e.m_bSound[t] = 0,
            a == e.m_iCurWndChannel && (e.m_iCurWndChannel = - 1, $('#Ezoom').attr('src', '../images/public/VideoBottom/disabled/DisZoom.png'), $('#Ezoom').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'laEnableZoom')), $('#zoom3D').attr('src', '../images/public/VideoBottom/disabled/Dis3D.png'), $('#zoom3D').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'Start3DZoom')), e.ResetVideoParam())
        }
        e.m_bAllRecord = !1,
        e.m_bAllPlay = !1,
        $('#play').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'paStartAllView')),
        $('#play').attr('src', '../images/public/VideoBottom/normal/RealPlayAll.png')
    },
    this.RecordAll = function () {
        if (e.m_bAllRecord) e.StopRecordAll();
         else for (var t = 0; g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum > t; t++) {
            var a = t + 1,
            o = document.getElementById('Record' + a + 'Img');
            if (0 == e.m_bChannelRecord[t] && 1 == e.m_bChannelPlay[t]) {
                var n = new Date,
                r = '',
                i = '';
                i = 0 > g_oCommon.m_szHostName.indexOf('[') ? g_oCommon.m_szHostName : g_oCommon.m_szHostName.substring(1, g_oCommon.m_szHostName.length - 1).replaceAll(':', '.'),
                r = 9 >= a ? i + '_0' + a + '_' + n.Format('yyyyMMddhhmmssS')  : i + '_' + a + '_' + n.Format('yyyyMMddhhmmssS');
                var s = g_oCommon.m_PreviewOCX.HWP_StartSave(e.m_iChannelWindow[t], r);
                if (0 == s) o.src = '../images/public/ICON/recording.png',
                o.title = parent.translator.translateNode(e.m_lxdPreview, 'stoprecord'),
                e.m_bChannelRecord[t] = 1;
                 else if ( - 2 == s) return e.m_bIsDiskFreeSpaceEnough = !1,
                alert(parent.translator.translateNode(e.m_lxdPreview, 'FreeSpaceTips')),
                void 0
            }
        }
        e.m_bAllRecord = !1;
        for (var m = 0; g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum > m; m++) if (e.m_bChannelRecord[m]) {
            e.m_bAllRecord = !0;
            break
        }
        e.m_bAllRecord ? ($('#record').attr('src', '../images/public/VideoBottom/normal/StopRecordAll.png'), $('#record').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'stopAllRecord')), e.m_bIsDiskFreeSpaceEnough = !0)  : ($('#record').attr('src', '../images/public/VideoBottom/normal/RecordAll.png'), $('#record').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'paStartAllRecord')))
    },
    this.StopRecordAll = function () {
        for (var t = 0; g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum > t; t++) {
            var a = t + 1,
            o = parent.translator.translateNode(e.m_lxdPreview, 'jsRecord'),
            n = g_oCommon.m_PreviewOCX.HWP_StopSave(e.m_iChannelWindow[t]);
            0 == n && (document.getElementById('Record' + a + 'Img').src = '../images/public/ICON/record.png', document.getElementById('Record' + a + 'Img').title = o, e.m_bChannelRecord[t] = 0, window.parent.g_oMain.showTipsDiv('', parent.translator.translateNode(e.m_lxdPreview, 'jsRecordAllSucc')))
        }
    },
    this.CapturePicture = function () {
        if (e.m_bChannelPlay[e.m_iCurWndChannel - 1]) {
            var t = new Date,
            a = '',
            o = '';
            o = 0 > g_oCommon.m_szHostName.indexOf('[') ? g_oCommon.m_szHostName : g_oCommon.m_szHostName.substring(1, g_oCommon.m_szHostName.length - 1).replaceAll(':', '.'),
            a = 9 >= e.m_iWndChannel[e.m_iCurWnd] ? o + '_0' + e.m_iWndChannel[e.m_iCurWnd] + '_' + t.Format('yyyyMMddhhmmssS')  : o + '_' + e.m_iWndChannel[e.m_iCurWnd] + '_' + t.Format('yyyyMMddhhmmssS');
            var n = g_oCommon.m_PreviewOCX.HWP_CapturePicture(e.m_iCurWnd, a);
            if (0 == n) window.parent.g_oMain.showTipsDiv('', parent.translator.translateNode(e.m_lxdPreview, 'jsCaptureSucc'));
             else if ( - 1 == n) {
                var r = g_oCommon.m_PreviewOCX.HWP_GetLastError();
                10 == r || 9 == r ? alert(parent.translator.translateNode(e.m_lxdPreview, 'jsCreateFileFailed'))  : alert(parent.translator.translateNode(e.m_lxdPreview, 'capturefailed'))
            } else - 2 == n ? alert(parent.translator.translateNode(e.m_lxdPreview, 'FreeSpaceTips'))  : - 3 == n && alert(parent.translator.translateNode(e.m_lxdPreview, 'jsCreateFileFailed'))
        }
    },
    this.SetFontColor = function (e) {
        for (var t = 0; g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum > t; t++) document.getElementById('Selected' + (t + 1) + 'color').style.color = t + 1 == e ? '#FF0000' : '#000000'
    },
    this.MouseOverPlay = function () {
        e.m_bAllPlay ? $('#play').attr('src', '../images/public/VideoBottom/select/SelStopAll.png')  : $('#play').attr('src', '../images/public/VideoBottom/select/SelRealPlayAll.png')
    },
    this.MouseOutPlay = function () {
        e.m_bAllPlay ? $('#play').attr('src', '../images/public/VideoBottom/normal/StopAll.png')  : $('#play').attr('src', '../images/public/VideoBottom/normal/RealPlayAll.png')
    },
    this.MouseOverZoom = function () {
        e.m_bChannelPlay[[e.m_iWndChannel[e.m_iCurWnd]] - 1] ? e.m_bEnableEZoom[e.m_iCurWnd] ? $('#Ezoom').attr('src', '../images/public/VideoBottom/select/SelZoomEnable.png')  : $('#Ezoom').attr('src', '../images/public/VideoBottom/select/SelZoom.png')  : $('#Ezoom').attr('src', '../images/public/VideoBottom/disabled/DisZoom.png')
    },
    this.MouseOutZoom = function () {
        e.m_bChannelPlay[[e.m_iWndChannel[e.m_iCurWnd]] - 1] ? e.m_bEnableEZoom[e.m_iCurWnd] ? $('#Ezoom').attr('src', '../images/public/VideoBottom/normal/ZoomEnable_normal.png')  : $('#Ezoom').attr('src', '../images/public/VideoBottom/normal/Zoom_normal.png')  : $('#Ezoom').attr('src', '../images/public/VideoBottom/disabled/DisZoom.png')
    },
    this.MouseOver3DZoom = function () {
        e.m_bChannelPlay[[e.m_iWndChannel[e.m_iCurWnd]] - 1] ? e.m_bEnable3DZoom[e.m_iCurWnd] ? $('#zoom3D').attr('src', '../images/public/VideoBottom/select/Sel3DEnable.png')  : $('#zoom3D').attr('src', '../images/public/VideoBottom/select/Sel3Dnormal.png')  : $('#zoom3D').attr('src', '../images/public/VideoBottom/disabled/Dis3D.png')
    },
    this.MouseOut3DZoom = function () {
        e.m_bChannelPlay[[e.m_iWndChannel[e.m_iCurWnd]] - 1] ? e.m_bEnable3DZoom[e.m_iCurWnd] ? $('#zoom3D').attr('src', '../images/public/VideoBottom/normal/3DEnable.png')  : $('#zoom3D').attr('src', '../images/public/VideoBottom/normal/3Dnormal.png')  : $('#zoom3D').attr('src', '../images/public/VideoBottom/disabled/Dis3D.png')
    },
    this.MouseOverPlayRec = function () {
        e.m_bAllRecord ? $('#record').attr('src', '../images/public/VideoBottom/select/SelStopRecordAll.png')  : $('#record').attr('src', '../images/public/VideoBottom/select/SelRecordAll.png')
    },
    this.MouseOutPlayRec = function () {
        e.m_bAllRecord ? $('#record').attr('src', '../images/public/VideoBottom/normal/StopRecordAll.png')  : $('#record').attr('src', '../images/public/VideoBottom/normal/RecordAll.png')
    },
    this.MouseOutWnd = function (t) {
        e.m_iCurWndNum == t * t ? $(window.parent.document.getElementById('wnd' + e.m_iCurWndNum)).attr('src', '../images/public/Arrange/PushWnd' + e.m_iCurWndNum + '.png')  : $(window.parent.document.getElementById('wnd' + t * t)).attr('src', '../images/public/Arrange/Wnd' + t * t + '.png')
    },
    this.ChangeWnd = function (t) {
        if (e.m_iCurWndNum != t * t) {
            e.m_iCurWndNum = t * t;
            var a = g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum;
            e.m_iCurWndNum >= a ? (e.m_bIsSupportPage = !1, $('#frontpage').attr('src', '../images/public/VideoBottom/disabled/Preview_DisFrontPage.png'), $('#nextpage').attr('src', '../images/public/VideoBottom/disabled/Preview_DisNextPage.png'))  : (e.m_bIsSupportPage = !0, $('#frontpage').attr('src', '../images/public/VideoBottom/normal/Preview_FrontPage.png'), $('#nextpage').attr('src', '../images/public/VideoBottom/normal/Preview_NextPage.png')),
            e.m_iTotalPageNum = 0 == a % e.m_iCurWndNum ? a / e.m_iCurWndNum : parseInt(a / e.m_iCurWndNum, 10) + 1,
            e.m_iCurrentPageNum = 0,
            g_oCommon.m_PreviewOCX.HWP_ArrangeWindow(t);
            for (var o = 1; 5 > o; o++) o != t ? $(window.parent.document.getElementById('wnd' + o * o)).attr('src', '../images/public/Arrange/Wnd' + o * o + '.png')  : $(window.parent.document.getElementById('wnd' + o * o)).attr('src', '../images/public/Arrange/PushWnd' + o * o + '.png')
        }
    },
    this.Frontpage = function () {
        if (!(g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum <= e.m_iCurWndNum)) if (1 == e.m_iCurWndNum) {
            var t = e.m_iWndChannel[0];
            e.StopRealPlayAll(),
            - 1 == t ? e.m_iCurrentPageNum-- : e.m_iCurrentPageNum = t - 1,
            1 > e.m_iCurrentPageNum && (e.m_iCurrentPageNum = e.m_iTotalPageNum),
            e.StartRealPlay(e.m_iCurrentPageNum, 0)
        } else {
            e.StopRealPlayAll(),
            e.m_iCurrentPageNum--,
            1 > e.m_iCurrentPageNum && (e.m_iCurrentPageNum = e.m_iTotalPageNum);
            for (var a = 0, o = (e.m_iCurrentPageNum - 1) * e.m_iCurWndNum; e.m_iCurrentPageNum * e.m_iCurWndNum > o && g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum > o; o++) e.StartRealPlay(o + 1, a),
            a++
        }
    },
    this.Nextpage = function () {
        if (!(g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum <= e.m_iCurWndNum)) if (1 == e.m_iCurWndNum) {
            var t = e.m_iWndChannel[0];
            e.StopRealPlayAll(),
            - 1 == t ? e.m_iCurrentPageNum++ : e.m_iCurrentPageNum = t + 1,
            e.m_iCurrentPageNum > e.m_iTotalPageNum && (e.m_iCurrentPageNum = 1),
            e.StartRealPlay(e.m_iCurrentPageNum, 0)
        } else {
            e.StopRealPlayAll(),
            e.m_iCurrentPageNum++,
            e.m_iCurrentPageNum > e.m_iTotalPageNum && (e.m_iCurrentPageNum = 1);
            for (var a = 0, o = (e.m_iCurrentPageNum - 1) * e.m_iCurWndNum; e.m_iCurrentPageNum * e.m_iCurWndNum > o && g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum > o; o++) e.StartRealPlay(o + 1, a),
            a++;
            e.SetFontColor(e.m_iWndChannel[e.m_iCurWnd])
        }
    },
    this.FullScreen = function () {
        g_oCommon.m_PreviewOCX.HWP_FullScreenDisplay(!0)
    },
    this.OpenSound = function () {
        if (1 == e.m_bChannelPlay[e.m_iCurWndChannel - 1]) if (e.m_bSound[e.m_iCurWndChannel - 1]) g_oCommon.m_PreviewOCX.HWP_CloseSound(),
        $('#opensound').unbind(),
        $('#opensound').bind({
            mouseover: function () {
                $(this).attr('src', '../images/public/VideoBottom/select/SelCloseSound.png')
            },
            mouseout: function () {
                $(this).attr('src', '../images/public/VideoBottom/normal/CloseSound.png')
            }
        }),
        $('#opensound').attr('src', '../images/public/VideoBottom/normal/CloseSound.png'),
        $('#opensound').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'opensound')),
        e.m_bSound[e.m_iCurWndChannel - 1] = 0,
        $(window.parent.document.getElementById('volumeDiv')).hide();
         else {
            for (var t = 0; 256 > t; t++) e.m_bSound[t] && (g_oCommon.m_PreviewOCX.HWP_CloseSound(), e.m_bSound[t] = 0);
            if (0 == g_oCommon.m_PreviewOCX.HWP_OpenSound(e.m_iCurWnd)) $('#opensound').unbind(),
            $('#opensound').bind({
                mouseover: function () {
                    $(this).attr('src', '../images/public/VideoBottom/select/SelOpenSound.png'),
                    $(window.parent.document.getElementById('volumeDiv')).show()
                },
                mouseout: function () {
                    $(this).attr('src', '../images/public/VideoBottom/normal/OpenSound.png')
                }
            }),
            $('#opensound').attr('src', '../images/public/VideoBottom/normal/OpenSound.png'),
            $('#opensound').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'closesound')),
            e.m_bSound[e.m_iCurWndChannel - 1] = 1,
            $(window.parent.document.getElementById('volumeDiv')).show(),
            e.SetVolume(50);
             else {
                var a = g_oCommon.m_PreviewOCX.HWP_GetLastError();
                25 == a && alert(parent.translator.translateNode(e.m_lxdPreview, 'jsOpenSoundFailed'))
            }
        }
    },
    this.SetVolume = function (t) {
        0 == g_oCommon.m_PreviewOCX.HWP_SetVolume(e.m_iCurWnd, t) && (window.parent.m_oSliderVolume.wsetValue(t), window.parent.m_oSliderVolume.setTitle('' + t))
    },
    "",
    this.changeLanguage = function (t) {
        e.m_lxdPreview = parent.translator.getLanguageXmlDoc('Preview', t),
        parent.translator.appendLanguageXmlDoc(e.m_lxdPreview, parent.g_oMain.m_lxdMain),
        parent.translator.translatePage(e.m_lxdPreview, document),
        window.parent.document.title = parent.translator.translateNode(e.m_lxdPreview, 'title'),
        g_oCommon.m_szExit = parent.translator.translateNode(e.m_lxdPreview, 'exit'),
        $('#ptzZoomIn').attr('title', g_oCommon.getNodeValue('laZoom') + ' +'),
        $('#ptzZoomOut').attr('title', g_oCommon.getNodeValue('laZoom') + ' -'),
        $('#laZoom').attr('title', g_oCommon.getNodeValue('laZoom')),
        $('#ptzFocusOut').attr('title', g_oCommon.getNodeValue('laFocus') + ' +'),
        $('#ptzFocusIn').attr('title', g_oCommon.getNodeValue('laFocus') + ' -'),
        $('#laFocus').attr('title', g_oCommon.getNodeValue('laFocus')),
        $('#ptzIrisOpen').attr('title', g_oCommon.getNodeValue('laIris') + ' +'),
        $('#ptzIrisClose').attr('title', g_oCommon.getNodeValue('laIris') + ' -'),
        $('#laIris').attr('title', g_oCommon.getNodeValue('laIris')),
        $('#ExcutePreset').attr('title', g_oCommon.getNodeValue('ExcutePreset')),
        $('#SetPreset').attr('title', g_oCommon.getNodeValue('SetPreset')),
        $('#ImgStartPatrol').attr('title', g_oCommon.getNodeValue('Start')),
        $('#ImgStopPatrol').attr('title', g_oCommon.getNodeValue('stop')),
        $('#ImgSavePatrol').attr('title', g_oCommon.getNodeValue('laSaveBtn')),
        $('#ImgStartPattern').attr('title', g_oCommon.getNodeValue('Start')),
        $('#ImgStopPattern').attr('title', g_oCommon.getNodeValue('stop')),
        $('#StartPatternRecord').attr('title', g_oCommon.getNodeValue('StartSave')),
        $('#StopPatternRecord').attr('title', g_oCommon.getNodeValue('StopSave')),
        $('#light').attr('title', g_oCommon.getNodeValue('laLight')),
        $('#rain').attr('title', g_oCommon.getNodeValue('laWiper')),
        $('#menu').attr('title', g_oCommon.getNodeValue('laMenu'));
        for (var a = 0; g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum + g_oCommon.m_iZeroChanNum > a; a++) 1 == e.m_bChannelPlay[a] ? $('#Camera' + (a + 1) + 'Img').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'stoppreview'))  : $('#Camera' + (a + 1) + 'Img').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'jsPreview')),
        e.m_bChannelRecord[a] ? $('#Record' + (a + 1) + 'Img').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'stoprecord'))  : $('#Record' + (a + 1) + 'Img').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'jsRecord'));
        e.m_bAllPlay ? $('#play').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'geStopAllViewing'))  : $('#play').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'paStartAllView')),
        e.m_bAllRecord ? $('#record').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'stopAllRecord'))  : $('#record').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'paStartAllRecord')),
        e.m_bSound[e.m_iCurWndChannel - 1] ? $('#opensound').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'closesound'))  : $('#opensound').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'opensound')),
        0 == g_oCommon.m_bTalk ? $('#voiceTalk').attr('title', g_oCommon.getNodeValue('voiceTalk'))  : $('#voiceTalk').attr('title', g_oCommon.getNodeValue('StopvoiceTalk')),
        e.m_bEnableEZoom[e.m_iCurWndChannel - 1] ? $('#Ezoom').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'laDisableZoom'))  : $('#Ezoom').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'laEnableZoom')),
        e.m_bEnable3DZoom[e.m_iCurWndChannel - 1] ? $('#zoom3D').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'Stop3DZoom'))  : $('#zoom3D').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'Start3DZoom')),
        $('#fullscreen').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'fullscreen')),
        e.m_oSliderPtzSpd.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'ptzSpeed') + ':' + (m_iPtzSpeed > 90 ? 7 : parseInt(m_iPtzSpeed / 15))),
        e.m_oSliderBright.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laBrightness') + ':' + e.m_oSliderBright.getValue()),
        e.m_oSliderContrast.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laContrast') + ':' + e.m_oSliderContrast.getValue()),
        e.m_oSliderSaturation.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laSaturation') + ':' + e.m_oSliderSaturation.getValue()),
        e.m_oSliderHue.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laHue') + ':' + e.m_oSliderHue.getValue()),
        $('#VideoDefault').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'geDefault')),
        $('#VideoDefault').html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + parent.translator.translateNode(e.m_lxdPreview, 'geDefault')),
        e.InitPatrolLan(),
        e.InitPresetListLan()
    },
    this.GetVideoParam = function (t) {
        if ( - 1 != t) {
            var a = g_oCommon.m_iChannelId[t - 1];
            e.GetVideoParamCab(t);
            var o = '';
            if (g_oCommon.m_iAnalogChannelNum >= t) o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/Image/channels/' + a;
             else {
                if (!(g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum >= t)) return e.ResetVideoParam(),
                void 0;
                o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/ImageProxy/channels/' + a
            }
            $.ajax({
                type: 'get',
                url: o,
                timeout: 15000,
                async: !1,
                username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                success: function (t, a, o) {
                    e.m_szXmlStr = o.responseText,
                    e.m_iBright = $(t).find('brightnessLevel').length > 0 ? parseInt($(t).find('brightnessLevel').eq(0).text(), 10)  : 0,
                    e.m_iContrast = $(t).find('contrastLevel').length > 0 ? parseInt($(t).find('contrastLevel').eq(0).text(), 10)  : 0,
                    e.m_iSaturation = $(t).find('saturationLevel').length > 0 ? parseInt($(t).find('saturationLevel').eq(0).text(), 10)  : 0,
                    e.m_iHue = $(t).find('hueLevel').length > 0 ? parseInt($(t).find('hueLevel').eq(0).text(), 10)  : 0,
                    e.m_bSupportScene ? ($(document.getElementsByName('raVideoParamMode')).each(function () {
                        this.value == $(t).find('imageMode').eq(0).text() && (this.checked = !0)
                    }), e.m_iSharpness = parseInt($(t).find('sharpnessLevel').eq(0).text(), 10), e.m_iNoiseReduce = parseInt($(t).find('generalLevel').eq(0).text(), 10))  : (e.m_iSharpness = 0, e.m_iNoiseReduce = 0),
                    e.m_oSliderBright.wsetValue(e.m_iBright),
                    e.m_oSliderContrast.wsetValue(e.m_iContrast),
                    e.m_oSliderSaturation.wsetValue(e.m_iSaturation),
                    e.m_oSliderHue.wsetValue(e.m_iHue),
                    e.m_oSliderSharpness.wsetValue(e.m_iSharpness),
                    e.m_oSliderNoiseReduce.wsetValue(e.m_iNoiseReduce),
                    e.m_oSliderBright.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laBrightness') + ':' + e.m_oSliderBright.getValue()),
                    e.m_oSliderContrast.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laContrast') + ':' + e.m_oSliderContrast.getValue()),
                    e.m_oSliderSaturation.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laSaturation') + ':' + e.m_oSliderSaturation.getValue()),
                    e.m_oSliderHue.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laHue') + ':' + e.m_oSliderHue.getValue()),
                    e.m_oSliderSharpness.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laSharpness') + ':' + e.m_iSharpness),
                    e.m_oSliderNoiseReduce.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laNoiseReduce') + ':' + e.m_iNoiseReduce)
                },
                error: function () {
                    e.m_szXmlStr = ''
                }
            })
        }
    },
    this.SetVideoParamBri = function (t) {
        if ( - 1 == e.m_iCurWndChannel || e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum) return e.m_iBright = e.m_iBrightMin,
        void 0;
        if (!(t > e.m_iBrightMax || e.m_iBrightMin > t)) {
            var a = g_oCommon.parseXmlFromStr(e.m_szXmlStr);
            $(a).find('brightnessLevel').length > 0 ? ($(a).find('brightnessLevel').eq(0).text(t), e.SetVideoParam(e.m_iCurWndChannel, a))  : (e.m_iBright = e.m_iBrightMin, e.m_oSliderBright.wsetValue(e.m_iBright), e.m_oSliderBright.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laBrightness') + ':' + e.m_oSliderBright.getValue()))
        }
    },
    this.SetVideoParamCon = function (t) {
        if ( - 1 == e.m_iCurWndChannel || e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum) return e.m_iContrast = e.m_iContrastMin,
        void 0;
        if (!(t > e.m_iContrastMax || e.m_iContrastMin > t)) {
            var a = g_oCommon.parseXmlFromStr(e.m_szXmlStr);
            $(a).find('contrastLevel').length > 0 ? ($(a).find('contrastLevel').eq(0).text(t), e.SetVideoParam(e.m_iCurWndChannel, a))  : (e.m_iContrast = e.m_iContrastMin, e.m_oSliderContrast.wsetValue(e.m_iContrast), e.m_oSliderContrast.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laContrast') + ':' + e.m_oSliderContrast.getValue()))
        }
    },
    this.SetVideoParamSat = function (t) {
        if ( - 1 == e.m_iCurWndChannel || e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum) return e.m_iSaturation = e.m_iSaturationMin,
        void 0;
        if (!(t > e.m_iSaturationMax || e.m_iSaturationMin > t)) {
            var a = g_oCommon.parseXmlFromStr(e.m_szXmlStr);
            $(a).find('saturationLevel').length > 0 ? ($(a).find('saturationLevel').eq(0).text(t), e.SetVideoParam(e.m_iCurWndChannel, a))  : (e.m_iSaturation = e.m_iSaturationMin, e.m_oSliderSaturation.wsetValue(e.m_iSaturation), e.m_oSliderSaturation.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laSaturation') + ':' + e.m_oSliderSaturation.getValue()))
        }
    },
    this.SetVideoParamHue = function (t) {
        if ( - 1 == e.m_iCurWndChannel || e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum) return e.m_iHue = e.m_iHueMin,
        void 0;
        if (!(t > e.m_iHueMax || e.m_iHueMin > t)) {
            var a = g_oCommon.parseXmlFromStr(e.m_szXmlStr);
            $(a).find('hueLevel').length > 0 ? ($(a).find('hueLevel').eq(0).text(t), e.SetVideoParam(e.m_iCurWndChannel, a))  : (e.m_iHue = e.m_iHueMin, e.m_oSliderHue.wsetValue(e.m_iHue), e.m_oSliderHue.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laHue') + ':' + e.m_oSliderHue.getValue()))
        }
    },
    this.SetVideoParamSharp = function (t) {
        if ( - 1 == e.m_iCurWndChannel || e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum) return e.m_iSharpness = e.m_iSharpnessMin,
        void 0;
        if (!(t > e.m_iSharpnessMax || e.m_iSharpnessMin > t)) {
            var a = g_oCommon.parseXmlFromStr(e.m_szXmlStr);
            $(a).find('sharpnessLevel').length > 0 ? ($(a).find('sharpnessLevel').eq(0).text(t), e.SetVideoParam(e.m_iCurWndChannel, a))  : (e.m_iSharpness = e.m_iSharpnessMin, e.m_oSliderSharpness.wsetValue(e.m_iSharpness), e.m_oSliderSharpness.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laSharpness') + ':' + e.m_oSliderSharpness.getValue()))
        }
    },
    this.SetVideoParamNoise = function (t) {
        if ( - 1 == e.m_iCurWndChannel || e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum) return e.m_iNoiseReduce = e.m_iNoiseReduceMin,
        void 0;
        if (!(t > e.m_iNoiseReduceMax || e.m_iNoiseReduceMin > t)) {
            var a = g_oCommon.parseXmlFromStr(e.m_szXmlStr);
            $(a).find('generalLevel').length > 0 ? ($(a).find('generalLevel').eq(0).text(t), e.SetVideoParam(e.m_iCurWndChannel, a))  : (e.m_iNoiseReduce = e.m_iNoiseReduceMin, e.m_oSliderNoiseReduce.wsetValue(e.m_iNoiseReduce), e.m_oSliderNoiseReduce.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laNoiseReduce') + ':' + e.m_oSliderNoiseReduce.getValue()))
        }
    },
    this.SetVideoDefault = function () {
        if (!( - 1 == e.m_iCurWndChannel || e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum) && confirm(parent.translator.translateNode(e.m_lxdPreview, 'jsVideoDefault'), parent.translator.translateNode(e.m_lxdPreview, 'jsTrue'), parent.translator.translateNode(e.m_lxdPreview, 'jsFalse'))) {
            if (e.m_bSupportScene) {
                var t = 0;
                $(document.getElementsByName('raVideoParamMode')).each(function (e) {
                    return this.checked ? (t = e, void 0)  : void 0
                }),
                e.m_iBright = e.m_iSceneParamDef[t][0],
                e.m_iContrast = e.m_iSceneParamDef[t][1],
                e.m_iSaturation = e.m_iSceneParamDef[t][2],
                e.m_iHue = e.m_iSceneParamDef[t][3],
                e.m_iSharpness = e.m_iSceneParamDef[t][4],
                e.m_iNoiseReduce = e.m_iSceneParamDef[t][5]
            } else e.m_iBright = e.m_iBrightDefault,
            e.m_iContrast = e.m_iContrastDefault,
            e.m_iSaturation = e.m_iSaturationDefault,
            e.m_iHue = e.m_iHueDefault,
            e.m_iSharpness = e.m_iSharpnessDefault,
            e.m_iNoiseReduce = e.m_iNoiseReduceDefault;
            var a = g_oCommon.parseXmlFromStr(e.m_szXmlStr);
            null !== a && ($(a).find('brightnessLevel').eq(0).text(e.m_iBright), $(a).find('contrastLevel').eq(0).text(e.m_iContrast), $(a).find('saturationLevel').eq(0).text(e.m_iSaturation), $(a).find('hueLevel').eq(0).text(e.m_iHue), $(a).find('sharpnessLevel').eq(0).text(e.m_iSharpness), $(a).find('generalLevel').eq(0).text(e.m_iNoiseReduce), e.m_oSliderBright.wsetValue(e.m_iBright), e.m_oSliderContrast.wsetValue(e.m_iContrast), e.m_oSliderSaturation.wsetValue(e.m_iSaturation), e.m_oSliderHue.wsetValue(e.m_iHue), e.m_oSliderBright.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laBrightness') + ':' + e.m_oSliderBright.getValue()), e.m_oSliderContrast.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laContrast') + ':' + e.m_oSliderContrast.getValue()), e.m_oSliderSaturation.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laSaturation') + ':' + e.m_oSliderSaturation.getValue()), e.m_oSliderHue.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laHue') + ':' + e.m_oSliderHue.getValue()), e.m_bSupportScene && (e.m_oSliderSharpness.wsetValue(e.m_iSharpness), e.m_oSliderNoiseReduce.wsetValue(e.m_iNoiseReduce), e.m_oSliderSharpness.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laSharpness') + ':' + e.m_oSliderSharpness.getValue()), e.m_oSliderNoiseReduce.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laNoiseReduce') + ':' + e.m_oSliderNoiseReduce.getValue())), e.SetVideoParam(e.m_iCurWndChannel, a))
        }
    },
    this.SetVideoParam = function (t, a) {
        var o = g_oCommon.m_iChannelId[t - 1],
        n = '';
        if (g_oCommon.m_iAnalogChannelNum >= t) n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/Image/channels/' + o;
         else {
            if (!(g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum >= t)) return e.ResetVideoParam(),
            void 0;
            n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/ImageProxy/channels/' + o
        }
        e.m_szXmlStr = g_oCommon.xmlToStr(a),
        $.ajax({
            type: 'put',
            url: n,
            timeout: 15000,
            async: !1,
            processData: !1,
            data: a,
            username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
            password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
            error: function (t) {
                e.GetVideoParam(e.m_iCurWndChannel),
                e.setState(t)
            },
            success: function () {
                e.GetVideoParam(e.m_iCurWndChannel)
            }
        })
    },
    this.SetZoomInStart = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><PTZData version=\'1.0\' xmlns=\'urn:psialliance-org\'><zoom>' + m_iPtzSpeed + '</zoom>' + '</PTZData>',
                o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + t + '/continuous';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + t + '/continuous';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.setState = function (t) {
        var a = t.responseXML,
        o = $(a).find('subStatusCode').eq(0).text();
        'lowPrivilege' === o ? alert(parent.translator.translateNode(e.m_lxdPreview, 'jsNoOperationRight'))  : 'notSupport' === o ? alert(parent.translator.translateNode(e.m_lxdPreview, 'Error77'))  : alert(parent.translator.translateNode(e.m_lxdPreview, 'jsNetError'))
    },
    this.StopPTZAuto = function () {
        e.m_bPTZAuto[e.m_iCurWndChannel - 1] && e.ptzAuto()
    },
    this.SetZoomOutStart = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><PTZData version=\'1.0\' xmlns=\'urn:psialliance-org\'><zoom>' + - m_iPtzSpeed + '</zoom>' + '</PTZData>',
                o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + t + '/continuous';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + t + '/continuous';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.SetPTZStop = function (t) {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var a = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > a)) {
                var o;
                o = 0 == t ? '<?xml version=\'1.0\' encoding=\'UTF-8\'?><PTZData version=\'1.0\' xmlns=\'urn:psialliance-org\'><pan>0</pan><tilt>0</tilt></PTZData>' : '<?xml version=\'1.0\' encoding=\'UTF-8\'?><PTZData version=\'1.0\' xmlns=\'urn:psialliance-org\'><zoom>0</zoom></PTZData>';
                var n = g_oCommon.parseXmlFromStr(o);
                if (g_oCommon.m_iAnalogChannelNum >= a) var r = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + a + '/continuous';
                 else var r = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + a + '/continuous';
                $.ajax({
                    type: 'put',
                    url: r,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: n,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function () {
                    }
                })
            }
        }
    },
    this.SetPTZLeftStart = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><PTZData version=\'1.0\' xmlns=\'urn:psialliance-org\'><pan>' + - m_iPtzSpeed + '</pan>' + '<tilt>' + 0 + '</tilt>' + '</PTZData>',
                o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + t + '/continuous';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + t + '/continuous';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.SetPTZLeftUpStart = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><PTZData version=\'1.0\' xmlns=\'urn:psialliance-org\'><pan>' + - m_iPtzSpeed + '</pan>' + '<tilt>' + m_iPtzSpeed + '</tilt>' + '</PTZData>',
                o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + t + '/continuous';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + t + '/continuous';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.SetPTZLeftDownStart = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><PTZData version=\'1.0\' xmlns=\'urn:psialliance-org\'><pan>' + - m_iPtzSpeed + '</pan>' + '<tilt>' + - m_iPtzSpeed + '</tilt>' + '</PTZData>',
                o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + t + '/continuous';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + t + '/continuous';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.SetPTZRightStart = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><PTZData version=\'1.0\' xmlns=\'urn:psialliance-org\'><pan>' + m_iPtzSpeed + '</pan>' + '<tilt>' + 0 + '</tilt>' + '</PTZData>',
                o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + t + '/continuous';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + t + '/continuous';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.SetPTZRightUpStart = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><PTZData version=\'1.0\' xmlns=\'urn:psialliance-org\'><pan>' + m_iPtzSpeed + '</pan>' + '<tilt>' + m_iPtzSpeed + '</tilt>' + '</PTZData>',
                o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + t + '/continuous';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + t + '/continuous';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.SetPTZRightDownStart = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><PTZData version=\'1.0\' xmlns=\'urn:psialliance-org\'><pan>' + m_iPtzSpeed + '</pan>' + '<tilt>' + - m_iPtzSpeed + '</tilt>' + '</PTZData>',
                o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + t + '/continuous';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + t + '/continuous';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.SetPTZUpStart = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><PTZData version=\'1.0\' xmlns=\'urn:psialliance-org\'><pan>0</pan><tilt>' + m_iPtzSpeed + '</tilt>' + '</PTZData>',
                o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + t + '/continuous';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + t + '/continuous';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.SetPTZDownStart = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><PTZData version=\'1.0\' xmlns=\'urn:psialliance-org\'><pan>0</pan><tilt>' + - m_iPtzSpeed + '</tilt>' + '</PTZData>',
                o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + t + '/continuous';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + t + '/continuous';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.ptzAuto = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '';
                a = e.m_bPTZAuto[e.m_iCurWndChannel - 1] ? '<?xml version=\'1.0\' encoding=\'UTF-8\'?><autoPanData version=\'1.0\'><autoPan>0</autoPan></autoPanData>' : '<?xml version=\'1.0\' encoding=\'UTF-8\'?><autoPanData version=\'1.0\'><autoPan>' + m_iPtzSpeed + '</autoPan></autoPanData>';
                var o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + t + '/autoPan';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + t + '/autoPan';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    success: function () {
                        e.m_bPTZAuto[e.m_iCurWndChannel - 1] = !e.m_bPTZAuto[e.m_iCurWndChannel - 1],
                        e.m_bPTZAuto[e.m_iCurWndChannel - 1] ? $('#ptzAutoId').attr('src', '../images/ptz/auto.png')  : $('#ptzAutoId').attr('src', '../images/ptz/ptz_normal/auto.png')
                    },
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.SetFocusInStart = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><FocusData version=\'1.0\' xmlns=\'urn:psialliance-org\'><focus>' + m_iPtzSpeed + '</focus>' + '</FocusData>',
                o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/Image/channels/' + t + '/focus';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/ImageProxy/channels/' + t + '/focus';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.SetFocusStop = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><FocusData version=\'1.0\' xmlns=\'urn:psialliance-org\'><focus>0</focus></FocusData>',
                o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/Image/channels/' + t + '/focus';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/ImageProxy/channels/' + t + '/focus';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.SetFocusOutStart = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><FocusData version=\'1.0\' xmlns=\'urn:psialliance-org\'><focus>' + - m_iPtzSpeed + '</focus>' + '</FocusData>',
                o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/Image/channels/' + t + '/focus';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/ImageProxy/channels/' + t + '/focus';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.IrisInStart = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><IrisData version=\'1.0\' xmlns=\'urn:psialliance-org\'><iris>' + m_iPtzSpeed + '</iris>' + '</IrisData>',
                o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/Image/channels/' + t + '/iris';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/ImageProxy/channels/' + t + '/iris';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.IrisOutStart = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><IrisData version=\'1.0\' xmlns=\'urn:psialliance-org\'><iris>' + - m_iPtzSpeed + '</iris>' + '</IrisData>',
                o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/Image/channels/' + t + '/iris';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/ImageProxy/channels/' + t + '/iris';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.IrisStop = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><IrisData version=\'1.0\' xmlns=\'urn:psialliance-org\'><iris>0</iris></IrisData>',
                o = g_oCommon.parseXmlFromStr(a);
                if (g_oCommon.m_iAnalogChannelNum >= t) var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/Image/channels/' + t + '/iris';
                 else var n = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/ImageProxy/channels/' + t + '/iris';
                $.ajax({
                    type: 'put',
                    url: n,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: o,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.SetPresetFun = function (t) {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var a = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > a)) {
                var o = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><PTZPreset version=\'1.0\' xmlns=\'urn:psialliance-org\'><id>' + t + '</id><presetName>Camera' + a + 'Preset' + t + '</presetName></PTZPreset>',
                n = g_oCommon.parseXmlFromStr(o);
                if (g_oCommon.m_iAnalogChannelNum >= a) var r = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + a + '/presets/' + t;
                 else var r = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + a + '/presets/' + t;
                $.ajax({
                    type: 'put',
                    url: r,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: n,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.GotoPreset = function (t) {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            e.StopPTZAuto();
            var a = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > a)) {
                if (g_oCommon.m_iAnalogChannelNum >= a) var o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + a + '/presets/' + t + '/goto';
                 else var o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + a + '/presets/' + t + '/goto';
                $.ajax({
                    type: 'put',
                    url: o,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: null,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.InitSlider = function () {
        var t = 1;
        e.m_oSliderBright = new neverModules.modules.slider({
            targetId: 'VideoBright',
            sliderCss: 'imageSlider1',
            barCss: 'imageBar2',
            boxCss: 'boxBar',
            bBox: !0,
            step: t,
            min: e.m_iBrightMin,
            max: e.m_iBrightMax,
            hints: ''
        }),
        e.m_oSliderBright.create(),
        e.m_oSliderBright.wsetValue(e.m_iBrightMin),
        e.m_oSliderBright.onchange = function () {
            e.m_iBright != e.m_oSliderBright.getValue() && (e.m_iBright = e.m_oSliderBright.getValue(), 0 > e.m_iBright && (e.m_iBright = e.m_oSliderBright.step, e.m_oSliderBright.wsetValue(e.m_iBright)), e.m_oSliderBright.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laBrightness') + ':' + e.m_oSliderBright.getValue()))
        },
        e.m_oSliderBright.onend = function () {
            e.SetVideoParamBri(e.m_iBright)
        },
        e.m_oSliderContrast = new neverModules.modules.slider({
            targetId: 'VideoContrast',
            sliderCss: 'imageSlider1',
            barCss: 'imageBar2',
            boxCss: 'boxBar',
            bBox: !0,
            step: t,
            min: e.m_iContrastMin,
            max: e.m_iContrastMax,
            hints: ''
        }),
        e.m_oSliderContrast.create(),
        e.m_oSliderContrast.wsetValue(e.m_iContrastMin),
        e.m_oSliderContrast.onchange = function () {
            e.m_iContrast != e.m_oSliderContrast.getValue() && (e.m_iContrast = e.m_oSliderContrast.getValue(), 0 > e.m_iContrast && (e.m_iContrast = e.m_oSliderContrast.step, e.m_oSliderContrast.wsetValue(e.m_iContrast)), e.m_oSliderContrast.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laContrast') + ':' + e.m_oSliderContrast.getValue()))
        },
        e.m_oSliderContrast.onend = function () {
            e.SetVideoParamCon(e.m_iContrast)
        },
        e.m_oSliderSaturation = new neverModules.modules.slider({
            targetId: 'VideoSaturation',
            sliderCss: 'imageSlider1',
            barCss: 'imageBar2',
            step: t,
            boxCss: 'boxBar',
            bBox: !0,
            min: e.m_iSaturationMin,
            max: e.m_iSaturationMax,
            hints: ''
        }),
        e.m_oSliderSaturation.create(),
        e.m_oSliderSaturation.wsetValue(e.m_iSaturationMin),
        e.m_oSliderSaturation.onchange = function () {
            e.m_iSaturation != e.m_oSliderSaturation.getValue() && (e.m_iSaturation = e.m_oSliderSaturation.getValue(), 0 > e.m_iSaturation && (e.m_iSaturation = e.m_oSliderSaturation.step, e.m_oSliderSaturation.wsetValue(e.m_iSaturation)), e.m_oSliderSaturation.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laSaturation') + ':' + e.m_oSliderSaturation.getValue()))
        },
        e.m_oSliderSaturation.onend = function () {
            e.SetVideoParamSat(e.m_iSaturation)
        },
        e.m_oSliderHue = new neverModules.modules.slider({
            targetId: 'VideoHue',
            sliderCss: 'imageSlider1',
            barCss: 'imageBar2',
            boxCss: 'boxBar',
            bBox: !0,
            step: t,
            min: e.m_iHueMin,
            max: e.m_iHueMax,
            hints: ''
        }),
        e.m_oSliderHue.create(),
        e.m_oSliderHue.wsetValue(e.m_iHueMin),
        e.m_oSliderHue.onchange = function () {
            e.m_iHue != e.m_oSliderHue.getValue() && (e.m_iHue = e.m_oSliderHue.getValue(), 0 > e.m_iHue && (e.m_iHue = e.m_oSliderHue.step, e.m_oSliderHue.wsetValue(e.m_iHue)), e.m_oSliderHue.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laHue') + ':' + e.m_oSliderHue.getValue()))
        },
        e.m_oSliderHue.onend = function () {
            e.SetVideoParamHue(e.m_iHue)
        },
        e.m_oSliderPtzSpd = new neverModules.modules.slider({
            targetId: 'ptzSlider',
            sliderCss: 'imageSlider2',
            barCss: 'imageBar1',
            min: 1,
            max: 7,
            hints: ''
        }),
        e.m_oSliderPtzSpd.create(),
        e.m_oSliderPtzSpd.wsetValue(4),
        m_iPtzSpeed = 60,
        //e.m_oSliderPtzSpd.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'ptzSpeed') + ':' + (m_iPtzSpeed > 90 ? 7 : parseInt(m_iPtzSpeed / 15))),
        e.m_oSliderPtzSpd.onchange = function () {
            var t = e.m_oSliderPtzSpd.getValue();
            m_iPtzSpeed = 7 > t ? 15 * t : 100,
            e.m_oSliderPtzSpd.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'ptzSpeed') + ':' + t)
        },
        e.m_oSliderSharpness = new neverModules.modules.slider({
            targetId: 'VideoSharpness',
            sliderCss: 'imageSlider1',
            barCss: 'imageBar2',
            boxCss: 'boxBar',
            bBox: !0,
            step: t,
            min: e.m_iSharpnessMin,
            max: e.m_iSharpnessMax,
            hints: ''
        }),
        e.m_oSliderSharpness.create(),
        e.m_oSliderSharpness.wsetValue(e.m_iSharpnessMin),
        e.m_oSliderSharpness.onchange = function () {
            e.m_iSharpness != e.m_oSliderSharpness.getValue() && (e.m_iSharpness = e.m_oSliderSharpness.getValue(), 0 > e.m_iSharpness && (e.m_iSharpness = e.m_oSliderSharpness.step, e.m_oSliderSharpness.wsetValue(e.m_iSharpness)), e.m_oSliderSharpness.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laSharpness') + ':' + e.m_oSliderSharpness.getValue()))
        },
        e.m_oSliderSharpness.onend = function () {
            e.SetVideoParamSharp(e.m_iSharpness)
        },
        e.m_oSliderNoiseReduce = new neverModules.modules.slider({
            targetId: 'VideoNoiseReduce',
            sliderCss: 'imageSlider1',
            barCss: 'imageBar2',
            boxCss: 'boxBar',
            bBox: !0,
            step: t,
            min: e.m_iNoiseReduceMin,
            max: e.m_iNoiseReduceMax,
            hints: ''
        }),
        e.m_oSliderNoiseReduce.create(),
        e.m_oSliderNoiseReduce.wsetValue(e.m_iNoiseReduceMin),
        e.m_oSliderNoiseReduce.onchange = function () {
            e.m_iNoiseReduce != e.m_oSliderNoiseReduce.getValue() && (e.m_iNoiseReduce = e.m_oSliderNoiseReduce.getValue(), 0 > e.m_iNoiseReduce && (e.m_iNoiseReduce = e.m_oSliderNoiseReduce.step, e.m_oSliderNoiseReduce.wsetValue(e.m_iNoiseReduce)), e.m_oSliderNoiseReduce.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laNoiseReduce') + ':' + e.m_oSliderNoiseReduce.getValue()))
        },
        e.m_oSliderNoiseReduce.onend = function () {
            e.SetVideoParamNoise(e.m_iNoiseReduce)
        }
    },
    this.onClickVideoBar = function () {
        e.m_bVideoExpand ? ($('#video_left').css('background-color', '#9b9b9b'), $('#video_mid').css('background-color', '#c7c7c7'), $('#video_right').css('background-color', '#9b9b9b'), $('#video_direction').attr('src', '../images/ptz/direction_up.png'), $('#videoParam').hide(), $('#videoBottom').hide(), $('#Preset').show(), $('#presetParam').attr({
            height: 1
        }), $('#presetParam').attr({
            height: $(window).height() - $('#PTZ').height() - 63
        }), e.m_bVideoExpand = !1)  : ($('#video_left').css('background-color', '#9b9b9b'), $('#video_mid').css('background-color', '#c7c7c7'), $('#video_right').css('background-color', '#9b9b9b'), $('#video_direction').attr('src', '../images/ptz/direction_down.png'), $('#videoParam').show(), $('#videoBottom').show(), $('#Preset').hide(), e.m_bVideoExpand = !0, e.m_oSliderBright.wsetValue(e.m_iBright), e.m_oSliderContrast.wsetValue(e.m_iContrast), e.m_oSliderSaturation.wsetValue(e.m_iSaturation), e.m_oSliderHue.wsetValue(e.m_iHue), 'none' !== $('#laSharpness_tr').css('display') && (e.m_oSliderSharpness.wsetValue(e.m_iSharpness), e.m_oSliderNoiseReduce.wsetValue(e.m_iNoiseReduce)))
    },
    this.selectPresetBar = function (t) {
        0 == t ? ($('#presetbg').css('backgroundImage', 'url(../images/ptz/tab_select.png)'), $('#cruisebg').css('backgroundImage', 'url(../images/ptz/tab_normal.png)'), $('#trackbg').css('backgroundImage', 'url(../images/ptz/tab_normal.png)'), $('#PresetPic').attr('src', '../images/ptz/preset-white.png'), $('#PatrolPic').attr('src', '../images/ptz/Cruise.png'), $('#PatternPic').attr('src', '../images/ptz/Track.png'), $('#presetbg').width(56), $('#cruisebg').width(46), $('#trackbg').width(46), $('#PresetArea').show(), $('#CruiseArea').hide(), $('#TrackArea').hide())  : 1 == t ? ($('#presetbg').css('backgroundImage', 'url(../images/ptz/tab_normal.png)'), $('#cruisebg').css('backgroundImage', 'url(../images/ptz/tab_select.png)'), $('#trackbg').css('backgroundImage', 'url(../images/ptz/tab_normal.png)'), $('#PresetPic').attr('src', '../images/ptz/preset.png'), $('#PatrolPic').attr('src', '../images/ptz/Cruise-white.png'), $('#PatternPic').attr('src', '../images/ptz/Track.png'), $('#presetbg').width(46), $('#cruisebg').width(56), $('#trackbg').width(46), $('#PresetArea').hide(), $('#CruiseArea').show(), $('#TrackArea').hide(), e.GetPatrol(parseInt($('#selectPatrol').val())))  : ($('#presetbg').css('backgroundImage', 'url(../images/ptz/tab_normal.png)'), $('#cruisebg').css('backgroundImage', 'url(../images/ptz/tab_normal.png)'), $('#trackbg').css('backgroundImage', 'url(../images/ptz/tab_select.png)'), $('#PresetPic').attr('src', '../images/ptz/preset.png'), $('#PatrolPic').attr('src', '../images/ptz/Cruise.png'), $('#PatternPic').attr('src', '../images/ptz/Track-white.png'), $('#presetbg').width(46), $('#cruisebg').width(46), $('#trackbg').width(56), $('#PresetArea').hide(), $('#CruiseArea').hide(), $('#TrackArea').show(), e.selectPattern(document.getElementById('PatternTable').rows[1].childNodes[0].childNodes[0].rows[0]))
    },
    this.InitPreset = function () {
        for (var e = g_oCommon.getNodeValue('laPreset'), t = '', a = 1; 257 > a; a++) t += '<tr height=\'1\' style=\'background-color:#CCC;\'><td></td></tr><tr height=\'30\'><td id=\'Preset' + a + '\'><table cellspacing=\'0\' cellpadding=\'0\' width=\'100%\' height=\'100%\'><tr height=\'30\' onClick=\'g_oPreviewInstance.selectPreset(this,' + a + ')\' style=\'cursor:pointer;\' onMouseOver="javascript:if(this!=g_oPreviewInstance.m_oSelectPreset){this.style.backgroundColor=\'#FBFBC2\';}" onMouseOut="javascript:if(this!=g_oPreviewInstance.m_oSelectPreset){this.style.backgroundColor=\'\'}"><td width=\'7%\' align=\'center\' valign=\'middle\'></td><td align=\'left\' valign=\'middle\' width=\'44%\'><span class=\'sp-presetname\' title=\'' + (e + ' ' + a) + '\'><label name=\'laPreset\'>' + e + '</label>' + ' ' + a + '</span></td><td width=\'17%\' align=\'center\' valign=\'middle\'></td><td width=\'17%\' align=\'center\' valign=\'middle\'></td></tr></table></td></tr>';
        $('#PresetTable').html(t)
    },
    this.selectPreset = function (t, a) {
        t != e.m_oSelectPreset && (null != e.m_oSelectPreset && (e.m_oSelectPreset.style.backgroundColor = '', e.m_oSelectPreset.cells[1].style.color = '', e.m_oSelectPreset.cells[2].innerHTML = '', e.m_oSelectPreset.cells[3].innerHTML = ''), t.style.backgroundColor = '#762727', t.cells[1].style.color = '#FFFFFF', t.cells[2].innerHTML = '<img id=\'ExcutePreset\' src=\'../images/ptz/gotoPreset_normal.png\' onClick=\'g_oPreviewInstance.GotoPreset(' + a + ')\' onMouseOver=\'this.src="../images/ptz/gotoPreset_push.png"\' onMouseOut=\'this.src="../images/ptz/gotoPreset_normal.png"\'/>', t.cells[3].innerHTML = '<img id=\'SetPreset\' src=\'../images/ptz/setPreset_normal.png\' onClick=\'g_oPreviewInstance.SetPresetFun(' + a + ')\' onMouseOver=\'this.src="../images/ptz/setPreset_push.png"\' onMouseOut=\'this.src="../images/ptz/setPreset_normal.png"\'\'/>', e.m_oSelectPreset = t, $('#ExcutePreset').attr('title', g_oCommon.getNodeValue('ExcutePreset')), $('#SetPreset').attr('title', g_oCommon.getNodeValue('SetPreset')))
    },
    this.InsertPresetList = function (t, a, o, n) {
        if (document.getElementById('CruiseTable').rows.length > 0 && - 1 != e.m_iSelectPatrolPreset) {
            var r = document.getElementById('CruiseTable').rows[2 * e.m_iSelectPatrolPreset - 1];
            r.style.backgroundColor = '',
            r.style.color = '',
            r.cells[5].childNodes[0].style.display = 'none',
            $(document.getElementById('CruiseTable').rows[2 * e.m_iSelectPatrolPreset - 1].cells[0].childNodes[0]).hide()
        }
        var i,
        s,
        m;
        s = document.getElementById('CruiseTable').insertRow(document.getElementById('CruiseTable').rows.length),
        s.style.height = '1px';
        for (var l = 0; 6 > l; l++) s.insertCell(l);
        s.style.backgroundColor = '#CCCCCC',
        i = document.getElementById('CruiseTable').insertRow(document.getElementById('CruiseTable').rows.length),
        $(i).bind({
            mouseover: function () {
                (this.rowIndex + 1) / 2 != e.m_iSelectPatrolPreset && $(this).css('backgroundColor', '#FBFBC2')
            },
            mouseout: function () {
                (this.rowIndex + 1) / 2 != e.m_iSelectPatrolPreset && $(this).css('backgroundColor', '')
            },
            click: function () {
                e.SelectPresetList((this.rowIndex + 1) / 2)
            }
        });
        var d = parent.translator.translateNode(e.m_lxdPreview, 'laPreset');
        e.m_iSelectPatrolPreset = document.getElementById('CruiseTable').rows.length / 2,
        i.style.height = '30px',
        i.style.cursor = 'pointer',
        i.align = 'center',
        i.style.backgroundColor = '#762727',
        i.style.color = '#FFFFFF';
        for (var g = 0; document.getElementById('CruiseTable').rows[0].cells.length > g; g++) switch (m = i.insertCell(g), g) {
            case 0:
                m.innerHTML = '<img src=\'../images/ptz/delete.png\' style=\'cursor:pointer;\' onClick=\'g_oPreviewInstance.DeletePresetList()\'/>',
                m.id = 'td' + t,
                m.style.width = '10%',
                m.align = 'center';
                break;
            case 1:
                m.innerHTML = '' + t,
                m.id = 'tdA' + t,
                m.style.width = '15%';
                break;
            case 2:
                m.innerHTML = '<label name="laPreset">' + d + '</label> ' + a,
                m.id = 'tdB' + t,
                m.style.width = '35%';
                break;
            case 3:
                m.innerHTML = o + 's',
                m.id = 'tdC' + t,
                m.align = 'center',
                m.style.width = '15%';
                break;
            case 4:
                m.innerHTML = '' + n,
                m.id = 'tdD' + t,
                m.style.width = '10%';
                break;
            case 5:
                m.innerHTML = '<img src=\'../images/ptz/setPreset_normal.png\' style=\'cursor:pointer;\' onClick=\'g_oPreviewInstance.EditPresetListDlg(this, 1, ' + a + ', ' + o + ', ' + n + ')\'/>',
                m.id = 'tdE' + t,
                m.style.width = '10%';
                break;
            default:
        }
        $('#PatrolPresetList').scrollTop($('#PatrolPresetList').height())
    },
    this.DeletePresetList = function () {
        var t = 2 * e.m_iSelectPatrolPreset - 1,
        a = document.getElementById('CruiseTable');
        if (!(0 >= a.rows.length || (a.deleteRow(t), a.deleteRow(t - 1), 0 >= a.rows.length))) {
            e.m_iSelectPatrolPreset > a.rows.length / 2 && ($(a.rows[a.rows.length - 1]).css('backgroundColor', '#762727'), $(a.rows[a.rows.length - 1]).css('color', '#FFFFFF'), $(a.rows[a.rows.length - 1].cells[0].childNodes[0]).show(), $(a.rows[a.rows.length - 1].cells[5].childNodes[0]).show(), e.m_iSelectPatrolPreset = a.rows.length / 2);
            for (var o = document.getElementById('CruiseTable').rows.length, n = 1; o / 2 >= n; n++) document.getElementById('CruiseTable').rows[2 * n - 1].cells[1].innerHTML = n
        }
    },
    this.SelectPresetList = function (t) {
        var a = document.getElementById('CruiseTable');
        a.rows.length / 2 >= t && t > 0 && ( - 1 != e.m_iSelectPatrolPreset && ($(a.rows[2 * e.m_iSelectPatrolPreset - 1]).css('backgroundColor', ''), $(a.rows[2 * e.m_iSelectPatrolPreset - 1]).css('color', ''), $(a.rows[2 * e.m_iSelectPatrolPreset - 1].cells[5].childNodes[0]).hide(), $(a.rows[2 * e.m_iSelectPatrolPreset - 1].cells[0].childNodes[0]).hide()), $(a.rows[2 * t - 1]).css('backgroundColor', '#762727'), $(a.rows[2 * t - 1]).css('color', '#FFFFFF'), $(a.rows[2 * t - 1].cells[0].childNodes[0]).show(), $(a.rows[2 * t - 1].cells[5].childNodes[0]).show(), e.m_iSelectPatrolPreset = t)
    },
    this.EditPresetListDlg = function (t, a, o, n, r) {
        if (0 == a) {
            if ($('#patrol_mid').html('<label name=\'laAddPatrolPath\'>' + g_oCommon.getNodeValue('laAddPatrolPath') + '</label>'), $('#CruiseTable tbody').children('tr').length >= 64) return $('#spanFootTips').html(g_oCommon.getNodeValue('CurrentAddTips') + '32 ' + g_oCommon.getNodeValue('laPreset')),
            setTimeout(function () {
                $('#spanFootTips').html('')
            }, 1500),
            void 0
        } else $('#patrol_mid').html('<label name=\'laModifyPatrolPath\'>' + g_oCommon.getNodeValue('laModifyPatrolPath') + '</label>');
        e.m_iOperateMode = a,
        e.m_oOperated = t,
        e.InitPresetList(),
        $('#SelectPreset').val(o),
        $('#PatrolTime').val(n),
        $('#PatrolSpeed').val(r),
        $('#EditPatrolPreset').css('right', '1px'),
        $('#EditPatrolPreset').css('top', $(t).offset().top - $('#EditPatrolPreset').height() + 5),
        $('#EditPatrolPreset').modal()
    },
    this.onPresetListDlgOk = function () {
        if (0 == e.m_iOperateMode) e.InsertPresetList(document.getElementById('CruiseTable').rows.length / 2 + 1, $('#SelectPreset').val(), $('#PatrolTime').val(), $('#PatrolSpeed').val());
         else {
            var t = parent.translator.translateNode(e.m_lxdPreview, 'laPreset'),
            a = e.m_oOperated.parentElement.parentElement;
            a.cells[2].innerHTML = '<label name="laPreset">' + t + '</label> ' + $('#SelectPreset').val(),
            a.cells[3].innerHTML = $('#PatrolTime').val() + 's',
            a.cells[4].innerHTML = $('#PatrolSpeed').val(),
            a.cells[5].innerHTML = '<img src=\'../images/ptz/setPreset_normal.png\' style=\'cursor:pointer;\' onClick=\'g_oPreviewInstance.EditPresetListDlg(this, 1, ' + $('#SelectPreset').val() + ', ' + $('#PatrolTime').val() + ', ' + $('#PatrolSpeed').val() + ')\'/>'
        }
        $.modal.impl.close()
    },
    this.GetPatrol = function (t) {
        if ('none' !== $('#cruisebg').css('display') && !(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum)) {
            var a = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (void 0 === a || null === a) return $('#CruiseTable').html(''),
            void 0;
            if (g_oCommon.m_iAnalogChannelNum >= a) var o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + a + '/patrols/' + t;
             else var o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + a + '/patrols/' + t;
            $.ajax({
                type: 'get',
                url: o,
                async: !1,
                timeout: 15000,
                username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                success: function (t) {
                    $('#CruiseTable').html(''),
                    parent.translator.translateNode(e.m_lxdPreview, 'laPreset');
                    for (var a = $(t).find('PatrolSequence').length, o = 0; a > o; o++) e.InsertPresetList(o + 1, $(t).find('presetID').eq(o).text(), parseInt($(t).find('delay').eq(o).text(), 10) / 1000, $(t).find('seqSpeed').eq(o).text())
                }
            })
        }
    },
    this.StartPatrol = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = $('#selectPatrol').val();
                if (g_oCommon.m_iAnalogChannelNum >= t) var o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + t + '/patrols/' + a + '/start';
                 else var o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + t + '/patrols/' + a + '/start';
                $.ajax({
                    type: 'put',
                    url: o,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: null,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.StopPatrol = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                var a = $('#selectPatrol').val();
                if (g_oCommon.m_iAnalogChannelNum >= t) var o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + t + '/patrols/' + a + '/stop';
                 else var o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + t + '/patrols/' + a + '/stop';
                $.ajax({
                    type: 'put',
                    url: o,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: null,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.SavePatrol = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                for (var a = $('#selectPatrol').val(), o = '', n = document.getElementById('CruiseTable').rows, r = 0, i = 0, s = 0, m = 0; n.length / 2 > m; m++) r = parseInt($(n[2 * m + 1].cells[2]).text().split(' ') [1], 10),
                i = 1000 * parseInt($(n[2 * m + 1].cells[3]).text().split('s') [0], 10),
                s = parseInt($(n[2 * m + 1].cells[4]).text(), 10),
                o += '<PatrolSequence><presetID>' + r + '</presetID><seqSpeed>' + s + '</seqSpeed>' + '<delay>' + i + '</delay></PatrolSequence>';
                o = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><PTZPatrol version=\'1.0\' xmlns=\'urn:psialliance-org\'><id>' + a + '</id><patrolName>patrol ' + (a > 9 ? a : '0' + a) + '</patrolName><PatrolSequenceList>' + o + '</PatrolSequenceList></PTZPatrol>';
                var l = g_oCommon.parseXmlFromStr(o);
                if (g_oCommon.m_iAnalogChannelNum >= t) var d = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + t + '/patrols/' + a;
                 else var d = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + t + '/patrols/' + a;
                $.ajax({
                    type: 'put',
                    url: d,
                    async: !1,
                    timeout: 15000,
                    processData: !1,
                    data: l,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.InitPattern = function () {
        for (var t = parent.translator.translateNode(e.m_lxdPreview, 'Pattern'), a = '', o = 1; 2 > o; o++) a += '<tr height=\'1\' style=\'background-color:#CCC;\'><td></td></tr><tr><td id=\'Pattern' + o + '\' height=\'30\'><table cellspacing=\'0\' cellpadding=\'0\' width=\'100%\' height=\'100%\'><tr onClick=\'g_oPreviewInstance.selectPattern(this)\' style=\'cursor:pointer;\' onMouseOver="javascript:if(this!=g_oPreviewInstance.m_oSelectPattern){this.style.backgroundColor=\'#FBFBC2\';}" onMouseOut="javascript:if(this!=g_oPreviewInstance.m_oSelectPattern){this.style.backgroundColor=\'\'}"><td align=\'center\' valign=\'middle\' width=\'40%\'><label name=\'Pattern\'>' + t + '</label>' + ' ' + o + '</td><td width=\'15%\' align=\'center\' valign=\'middle\'><img id=\'ImgStartPattern\' src=\'../images/ptz/PatternStart.png\' onClick=\'g_oPreviewInstance.StartPattern(' + o + ')\'  style=\'display:none;\'/></td><td width=\'15%\' align=\'center\' valign=\'middle\'><img id=\'ImgStopPattern\' src=\'../images/ptz/PatternStop.png\' onClick=\'g_oPreviewInstance.StopPattern(' + o + ')\' style=\'display:none;\'/></td><td width=\'15%\' align=\'center\' valign=\'middle\'><img id=\'StartPatternRecord\' src=\'../images/ptz/setPreset_normal.png\' onClick=\'g_oPreviewInstance.StartRecordPattern(' + o + ')\' style=\'display:none;\'/></td><td width=\'15%\' align=\'center\' valign=\'middle\'><img src=\'../images/ptz/PatternSave.png\' id=\'StopPatternRecord\' onClick=\'g_oPreviewInstance.StopRecordPattern(' + o + ')\' style=\'display:none;\'/></td></tr></table></td></tr>';
        $('#PatternTable').html(a)
    },
    this.selectPattern = function (t) {
        t != e.m_oSelectPattern && (null != e.m_oSelectPattern && (e.m_oSelectPattern.style.backgroundColor = '', e.m_oSelectPattern.cells[0].style.color = '', e.m_oSelectPattern.cells[1].childNodes[0].style.display = 'none', e.m_oSelectPattern.cells[2].childNodes[0].style.display = 'none', e.m_oSelectPattern.cells[3].childNodes[0].style.display = 'none', e.m_oSelectPattern.cells[4].childNodes[0].style.display = 'none'), t.style.backgroundColor = '#762727', t.cells[0].style.color = '#FFFFFF', t.cells[1].childNodes[0].style.display = '', t.cells[2].childNodes[0].style.display = '', t.cells[3].childNodes[0].style.display = '', t.cells[4].childNodes[0].style.display = '', e.m_oSelectPattern = t)
    },
    this.StartRecordPattern = function (t) {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            var a = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > a)) {
                if (g_oCommon.m_iAnalogChannelNum >= a) var o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + a + '/patterns/' + t + '/recordstart';
                 else var o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + a + '/patterns/' + t + '/recordstart';
                $.ajax({
                    type: 'PUT',
                    async: !0,
                    timeout: 15000,
                    url: o,
                    processData: !1,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.StopRecordPattern = function (t) {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            var a = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > a)) {
                if (g_oCommon.m_iAnalogChannelNum >= a) var o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + a + '/patterns/' + t + '/recordstop';
                 else var o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + a + '/patterns/' + t + '/recordstop';
                $.ajax({
                    type: 'PUT',
                    async: !0,
                    timeout: 15000,
                    url: o,
                    processData: !1,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.StartPattern = function (t) {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            var a = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > a)) {
                if (g_oCommon.m_iAnalogChannelNum >= a) var o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + a + '/patterns/' + t + '/run';
                 else var o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + a + '/patterns/' + t + '/run';
                $.ajax({
                    type: 'PUT',
                    async: !0,
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    timeout: 15000,
                    url: o,
                    processData: !1,
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.StopPattern = function (t) {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            var a = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > a)) {
                if (g_oCommon.m_iAnalogChannelNum >= a) var o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + a + '/patterns/' + t + '/stop';
                 else var o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + a + '/patterns/' + t + '/stop';
                $.ajax({
                    type: 'PUT',
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    async: !0,
                    timeout: 15000,
                    url: o,
                    processData: !1,
                    error: function (t) {
                        e.setState(t)
                    }
                })
            }
        }
    },
    this.GetPatrolsCab = function () {
        if (0 !== g_oCommon.m_iAnalogChannelNum) var t = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/1/patrols';
         else var t = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/1/patrols';
        $.ajax({
            type: 'get',
            username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
            password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
            async: !1,
            url: t,
            success: function (t) {
                var a = $(t).find('PTZPatrol').length;
                a > 0 || g_oCommon.m_bSupportPatrols ? ($('#cruisebg').show(), e.m_iPTZPatrolNum = a > 0 ? a : 4)  : $('#cruisebg').hide()
            },
            error: function () {
                $('#cruisebg').hide()
            }
        })
    },
    this.GetPatternCab = function () {
        if (0 !== g_oCommon.m_iAnalogChannelNum) var e = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/1/patterns';
         else var e = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/1/patterns';
        $.ajax({
            type: 'get',
            username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
            password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
            async: !1,
            url: e,
            complete: function (e) {
                200 == e.status ? $('#trackbg').show()  : $('#trackbg').hide()
            }
        })
    },
    this.GetVideoParamCab = function (t) {
        var a = g_oCommon.m_iChannelId[t - 1],
        o = '';
        if (g_oCommon.m_iAnalogChannelNum >= t) o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/Image/channels/' + a + '/capabilities';
         else {
            if (!(g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum >= t)) return e.m_bSupportScene = !1,
            void 0;
            o = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/ImageProxy/channels/' + a + '/capabilities'
        }
        $.ajax({
            type: 'GET',
            username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
            password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
            async: !1,
            url: o,
            success: function (t) {
                $(t).find('brightnessLevel').length > 0 && (e.m_iBrightDefault = parseInt($(t).find('brightnessLevel').eq(0).attr('def'), 10), e.m_iBrightMin = parseInt($(t).find('brightnessLevel').eq(0).attr('min'), 10), e.m_iBrightMax = parseInt($(t).find('brightnessLevel').eq(0).attr('max'), 10)),
                $(t).find('contrastLevel').length > 0 && (e.m_iContrastDefault = parseInt($(t).find('contrastLevel').eq(0).attr('def'), 10), e.m_iContrastMin = parseInt($(t).find('contrastLevel').eq(0).attr('min'), 10), e.m_iContrastMax = parseInt($(t).find('contrastLevel').eq(0).attr('max'), 10)),
                $(t).find('saturationLevel').length > 0 && (e.m_iSaturationDefault = parseInt($(t).find('saturationLevel').eq(0).attr('def'), 10), e.m_iSaturationMin = parseInt($(t).find('saturationLevel').eq(0).attr('min'), 10), e.m_iSaturationMax = parseInt($(t).find('saturationLevel').eq(0).attr('max'), 10)),
                $(t).find('hueLevel').length > 0 && (e.m_iHueDefault = parseInt($(t).find('hueLevel').eq(0).attr('def'), 10), e.m_iHueMin = parseInt($(t).find('hueLevel').eq(0).attr('min'), 10), e.m_iHueMax = parseInt($(t).find('hueLevel').eq(0).attr('max'), 10)),
                $(t).find('imageMode').length > 0 ? (e.m_bSupportScene = !0, $('#VideoParamMode_tr1').css('display', 'block'), $('#VideoParamMode_tr2').css('display', 'block'), $('#divisionShortLine_tr').css('display', 'block'), $('#laSharpness_tr').css('display', 'block'), $('#laNoiseReduce_tr').css('display', 'block'), e.m_iSharpnessMin = parseInt($(t).find('sharpnessLevel').eq(0).attr('min'), 10), e.m_iSharpnessMax = parseInt($(t).find('sharpnessLevel').eq(0).attr('max'), 10), e.m_iSharpnessDefault = parseInt($(t).find('sharpnessLevel').eq(0).attr('def'), 10), e.m_iNoiseReduceMin = parseInt($(t).find('generalLevel').eq(0).attr('min'), 10), e.m_iNoiseReduceMax = parseInt($(t).find('generalLevel').eq(0).attr('max'), 10), e.m_iNoiseReduceDefault = parseInt($(t).find('generalLevel').eq(0).attr('def'), 10), e.GetSceneParamDefault())  : (e.m_bSupportScene = !1, $('#VideoParamMode_tr1').css('display', 'none'), $('#VideoParamMode_tr2').css('display', 'none'), $('#divisionShortLine_tr').css('display', 'none'), $('#laSharpness_tr').css('display', 'none'), $('#laNoiseReduce_tr').css('display', 'none')),
                e.InitSlider()
            },
            error: function () {
                e.m_bSupportScene = !1,
                $('#VideoParamMode_tr1').css('display', 'none'),
                $('#VideoParamMode_tr2').css('display', 'none'),
                $('#divisionShortLine_tr').css('display', 'none'),
                $('#laSharpness_tr').css('display', 'none'),
                $('#laNoiseReduce_tr').css('display', 'none')
            }
        })
    },
    this.ResetVideoParam = function () {
        e.m_iBright = e.m_iBrightMin,
        e.m_iContrast = e.m_iContrastMin,
        e.m_iSaturation = e.m_iSaturationMin,
        e.m_iHue = e.m_iHueMin,
        e.m_iSharpness = e.m_iSharpnessMin,
        e.m_iNoiseReduce = e.m_iNoiseReduceMin,
        e.m_oSliderBright.wsetValue(e.m_iBright),
        e.m_oSliderContrast.wsetValue(e.m_iContrast),
        e.m_oSliderSaturation.wsetValue(e.m_iSaturation),
        e.m_oSliderHue.wsetValue(e.m_iHue),
        e.m_oSliderBright.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laBrightness') + ':' + e.m_oSliderBright.getValue()),
        e.m_oSliderContrast.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laContrast') + ':' + e.m_oSliderContrast.getValue()),
        e.m_oSliderSaturation.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laSaturation') + ':' + e.m_oSliderSaturation.getValue()),
        e.m_oSliderHue.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laHue') + ':' + e.m_oSliderHue.getValue()),
        e.m_bSupportScene ? ($('#VideoParamMode_tr1').css('display', 'block'), $('#VideoParamMode_tr2').css('display', 'block'), $('#divisionShortLine_tr').css('display', 'block'), $('#laSharpness_tr').css('display', 'block'), $('#laNoiseReduce_tr').css('display', 'block'), e.m_oSliderSharpness.wsetValue(e.m_iSharpness), e.m_oSliderNoiseReduce.wsetValue(e.m_iNoiseReduce), e.m_oSliderSharpness.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laSharpness') + ':' + e.m_oSliderSharpness.getValue()), e.m_oSliderNoiseReduce.setTitle(parent.translator.translateNode(e.m_lxdPreview, 'laNoiseReduce') + ':' + e.m_oSliderNoiseReduce.getValue()))  : ($('#VideoParamMode_tr1').css('display', 'none'), $('#VideoParamMode_tr2').css('display', 'none'), $('#divisionShortLine_tr').css('display', 'none'), $('#laSharpness_tr').css('display', 'none'), $('#laNoiseReduce_tr').css('display', 'none'))
    },
    this.GetSceneParamDefault = function () {
        var t = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/Image/channels/imageModes';
        $.ajax({
            type: 'get',
            username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
            password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
            async: !1,
            url: t,
            success: function (t) {
                for (var a = 0; $(t).find('ImageMode').length > a; a++) {
                    var o = 0,
                    n = $(t).find('type').eq(a).text();
                    o = 'standard' == n ? 0 : 'indoor' == n ? 1 : 'outdoor' == n ? 2 : 3,
                    e.m_iSceneParamDef[o][0] = parseInt($(t).find('brightnessLevel').eq(a).attr('def'), 10),
                    e.m_iSceneParamDef[o][1] = parseInt($(t).find('contrastLevel').eq(a).attr('def'), 10),
                    e.m_iSceneParamDef[o][2] = parseInt($(t).find('saturationLevel').eq(a).attr('def'), 10),
                    e.m_iSceneParamDef[o][3] = parseInt($(t).find('hueLevel').eq(a).attr('def'), 10),
                    e.m_iSceneParamDef[o][4] = parseInt($(t).find('sharpnessLevel').eq(a).attr('def'), 10),
                    e.m_iSceneParamDef[o][5] = parseInt($(t).find('deNoiseLevel').eq(a).attr('def'), 10)
                }
            }
        })
    },
    this.setScene = function (t) {
        var a = g_oCommon.parseXmlFromStr(e.m_szXmlStr),
        o = '';
        $(document.getElementsByName('raVideoParamMode')).each(function () {
            return this.checked ? (o = this.value, void 0)  : void 0
        }),
        $(a).find('imageMode').eq(0).text(o),
        $(a).find('brightnessLevel').eq(0).text(e.m_iSceneParamDef[t][0]),
        $(a).find('contrastLevel').eq(0).text(e.m_iSceneParamDef[t][1]),
        $(a).find('saturationLevel').eq(0).text(e.m_iSceneParamDef[t][2]),
        $(a).find('hueLevel').eq(0).text(e.m_iSceneParamDef[t][3]),
        $(a).find('sharpnessLevel').eq(0).text(e.m_iSceneParamDef[t][4]),
        $(a).find('generalLevel').eq(0).text(e.m_iSceneParamDef[t][5]),
        e.SetVideoParam(e.m_iCurWndChannel, a),
        e.GetVideoParam(e.m_iCurWndChannel)
    },
    this.EnableEZoom = function () {
        var t = 0;
        if (e.m_bChannelPlay[e.m_iCurWndChannel - 1]) {
            var a = !e.m_bEnableEZoom[e.m_iCurWnd];
            a ? (t = g_oCommon.m_PreviewOCX.HWP_EnableZoom(e.m_iCurWnd, 0), 0 == t && (e.m_bEnableEZoom[e.m_iCurWnd] = !0, $('#Ezoom').attr('src', '../images/public/VideoBottom/normal/ZoomEnable_normal.png'), $('#Ezoom').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'laDisableZoom'))))  : (g_oCommon.m_PreviewOCX.HWP_DisableZoom(e.m_iCurWnd), e.m_bEnableEZoom[e.m_iCurWnd] = !1, $('#Ezoom').attr('src', '../images/public/VideoBottom/normal/Zoom_normal.png'), $('#Ezoom').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'laEnableZoom')))
        }
    },
    this.Enable3DZoom = function () {
        var t = 0;
        if (e.m_bChannelPlay[e.m_iCurWndChannel - 1]) {
            var a = !e.m_bEnable3DZoom[e.m_iCurWnd];
            a ? (t = g_oCommon.m_PreviewOCX.HWP_EnableZoom(e.m_iCurWnd, 1), 0 == t && (e.m_bEnable3DZoom[e.m_iCurWnd] = !0, $('#zoom3D').attr('src', '../images/public/VideoBottom/normal/3DEnable.png'), $('#zoom3D').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'Stop3DZoom'))))  : (g_oCommon.m_PreviewOCX.HWP_DisableZoom(e.m_iCurWnd), e.m_bEnable3DZoom[e.m_iCurWnd] = !1, $('#zoom3D').attr('src', '../images/public/VideoBottom/normal/3Dnormal.png'), $('#zoom3D').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'Start3DZoom')))
        }
    },
    this.GetZeroEnLargeState = function () {
        var t = 1;
        - 1 != e.m_iCurWndChannel && (t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1]),
        $.ajax({
            type: 'get',
            url: g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/ZeroVideo/channels/' + t + '/enlarge',
            async: !1,
            timeout: 15000,
            username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
            password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
            success: function (t) {
                $(t).find('stat').length > 0 && (e.m_iEnLargeZeroChannel = 'normal' == $(t).find('stat').eq(0).text() ? 0 : 1)
            }
        })
    },
    this.SetZeroEnLargeState = function (t, a, o) {
        var n = 1;
        - 1 != e.m_iCurWndChannel && (n = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1]);
        var r = '<?xml version=\'1.0\' encoding=\'UTF-8\'?><ZeroVideoEnlarge><stat>' + (0 === parseInt(t, 10) ? 'normal' : 'enlarge') + '</stat>' + '<mousePosition><x>' + a + '</x><y>' + o + '</y></mousePosition>' + '</ZeroVideoEnlarge>',
        i = g_oCommon.parseXmlFromStr(r);
        $.ajax({
            type: 'put',
            username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
            password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
            timeout: 15000,
            url: g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/ZeroVideo/channels/' + n + '/enlarge',
            processData: !1,
            data: i,
            error: function (t) {
                e.setState(t)
            }
        })
    },
    this.switchStream = function (t) {
        var a = '#Stream' + t + 'Img',
        o = e.m_iChannelWindow[t - 1];
        e.m_iChannelStream[t - 1] = 1 === e.m_iChannelStream[t - 1] ? 0 : 1,
        e.m_bChannelPlay[t - 1] && (e.StopRealPlay(t), e.StartRealPlay(t, o)),
        0 === e.m_iChannelStream[t - 1] ? $(a).attr('src', '../images/public/ICON/main_stream.png').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'streamTypeOpt1'))  : $(a).attr('src', '../images/public/ICON/sub_stream.png').attr('title', parent.translator.translateNode(e.m_lxdPreview, 'streamTypeOpt2'))
    },
    this.setMenu = function () {
        if (!(e.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum || 0 > e.m_iCurWndChannel)) {
            var t = g_oCommon.m_iChannelId[e.m_iCurWndChannel - 1];
            if (!(0 > t)) {
                if (g_oCommon.m_iAnalogChannelNum >= t) var a = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/PTZCtrl/channels/' + t + '/menu';
                 else var a = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + t + '/menu';
                $.ajax({
                    type: 'put',
                    username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
                    password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
                    timeout: 15000,
                    url: a,
                    processData: !1,
                    data: null,
                    error: function () {
                    }
                })
            }
        }
    }
}
function GetSelectWndInfo(e) {
    var t = g_oCommon.parseXmlFromStr(e);
    if (g_oPreviewInstance.m_iCurWnd = parseInt(t.documentElement.getElementsByTagName('SelectWnd') [0].childNodes[0].nodeValue), g_oPreviewInstance.m_iCurWndChannel = g_oPreviewInstance.m_iWndChannel[g_oPreviewInstance.m_iCurWnd], g_oPreviewInstance.GetPatrol(parseInt($('#selectPatrol').val())), g_oPreviewInstance.m_iCurWndChannel > - 1 && 1 == g_oPreviewInstance.m_bChannelPlay[g_oPreviewInstance.m_iCurWndChannel - 1]) if (g_oPreviewInstance.m_iCurWndChannel > g_oCommon.m_iAnalogChannelNum + g_oCommon.m_iDigitalChannelNum) {
        if (g_oPreviewInstance.GetZeroEnLargeState(), - 1 !== g_oPreviewInstance.m_iEnLargeZeroChannel) try {
            g_oCommon.m_PreviewOCX.HWP_SetZeroChanInfo(1, g_oPreviewInstance.m_iEnLargeZeroChannel, g_oPreviewInstance.m_iCurWnd)
        } catch (a) {
        }
    } else try {
        g_oCommon.m_PreviewOCX.HWP_SetZeroChanInfo(0, 0, g_oPreviewInstance.m_iCurWnd)
    } catch (a) {
    }
    g_oPreviewInstance.m_bChannelPlay[g_oPreviewInstance.m_iCurWndChannel - 1] ? (g_oPreviewInstance.m_bEnableEZoom[g_oPreviewInstance.m_iCurWnd] ? ($('#Ezoom').attr('src', '../images/public/VideoBottom/normal/ZoomEnable_normal.png'), $('#Ezoom').attr('title', parent.translator.translateNode(g_oPreviewInstance.m_lxdPreview, 'laDisableZoom')))  : ($('#Ezoom').attr('src', '../images/public/VideoBottom/normal/Zoom_normal.png'), $('#Ezoom').attr('title', parent.translator.translateNode(g_oPreviewInstance.m_lxdPreview, 'laEnableZoom'))), g_oPreviewInstance.m_bEnable3DZoom[g_oPreviewInstance.m_iCurWnd] ? ($('#zoom3D').attr('src', '../images/public/VideoBottom/normal/3DEnable.png'), $('#zoom3D').attr('title', parent.translator.translateNode(g_oPreviewInstance.m_lxdPreview, 'Stop3DZoom')))  : ($('#zoom3D').attr('src', '../images/public/VideoBottom/normal/3Dnormal.png'), $('#zoom3D').attr('title', parent.translator.translateNode(g_oPreviewInstance.m_lxdPreview, 'Start3DZoom'))), g_oPreviewInstance.m_bPTZAuto[g_oPreviewInstance.m_iCurWndChannel - 1] ? $('#ptzAutoId').attr('src', '../images/ptz/auto.png')  : $('#ptzAutoId').attr('src', '../images/ptz/ptz_normal/auto.png'))  : ($('#Ezoom').attr('src', '../images/public/VideoBottom/disabled/DisZoom.png'), $('#Ezoom').attr('title', parent.translator.translateNode(g_oPreviewInstance.m_lxdPreview, 'laEnableZoom')), $('#zoom3D').attr('src', '../images/public/VideoBottom/disabled/Dis3D.png'), $('#zoom3D').attr('title', parent.translator.translateNode(g_oPreviewInstance.m_lxdPreview, 'Start3DZoom')), $('#ptzAutoId').attr('src', '../images/ptz/ptz_normal/auto.png')),
    g_oPreviewInstance.m_iCurWndChannel > - 1 && g_oPreviewInstance.m_bSound[g_oPreviewInstance.m_iCurWndChannel - 1] ? ($('#opensound').unbind(), $('#opensound').bind({
        mouseover: function () {
            $(this).attr('src', '../images/public/VideoBottom/select/SelOpenSound.png'),
            $(window.parent.document.getElementById('volumeDiv')).show()
        },
        mouseout: function () {
            $(this).attr('src', '../images/public/VideoBottom/normal/OpenSound.png')
        }
    }), $('#opensound').attr('src', '../images/public/VideoBottom/normal/OpenSound.png'), $('#opensound').attr('title', parent.translator.translateNode(g_oPreviewInstance.m_lxdPreview, 'closesound')))  : ($('#opensound').unbind(), $('#opensound').bind({
        mouseover: function () {
            $(this).attr('src', '../images/public/VideoBottom/select/SelCloseSound.png')
        },
        mouseout: function () {
            $(this).attr('src', '../images/public/VideoBottom/normal/CloseSound.png')
        }
    }), $('#opensound').attr('src', '../images/public/VideoBottom/normal/CloseSound.png'), $('#opensound').attr('title', parent.translator.translateNode(g_oPreviewInstance.m_lxdPreview, 'opensound')), $(window.parent.document.getElementById('volumeDiv')).hide()),
    g_oPreviewInstance.SetFontColor(g_oPreviewInstance.m_iCurWndChannel),
    - 1 != g_oPreviewInstance.m_iCurWndChannel ? g_oPreviewInstance.GetVideoParam(g_oPreviewInstance.m_iCurWndChannel)  : g_oPreviewInstance.ResetVideoParam()
}
function PluginEventHandler(e, t) {
    21 == e ? (g_oPreviewInstance.m_bIsDiskFreeSpaceEnough && (g_oPreviewInstance.m_bIsDiskFreeSpaceEnough = !1, setTimeout(function () {
        alert(parent.translator.translateNode(g_oPreviewInstance.m_lxdPreview, 'FreeSpaceTips'))
    }, 2000)), g_oPreviewInstance.StopRecord(g_oPreviewInstance.m_iWndChannel[t]))  : 3 == e ? (g_oCommon.m_bTalk = 0, document.getElementById('voiceTalk').src = '../images/public/ICON/speak_normal.png', document.getElementById('voiceTalk').title = parent.translator.translateNode(g_oPreviewInstance.m_lxdPreview, 'voiceTalk'), g_oCommon.m_PreviewOCX.HWP_StopVoiceTalk(), setTimeout(function () {
        alert(parent.translator.translateNode(g_oPreviewInstance.m_lxdPreview, 'VoiceTalkFailed'))
    }, 2000))  : 7 == e && (g_oPreviewInstance.m_bNoStreamSecret || (g_oPreviewInstance.m_bNoStreamSecret = !0, alert(parent.translator.translateNode(g_oPreviewInstance.m_lxdPreview, 'jsNoStreamSecret'))))
}
function SetZeroChanEnlarge(e) {
    var t = g_oCommon.parseXmlFromStr(e);
    - 1 != $(t).find('MCP_x').eq(0).text() && - 1 != $(t).find('MCP_y').eq(0).text() && 1 == $(t).find('BeState').eq(0).text() ? g_oPreviewInstance.SetZeroEnLargeState($(t).find('BeState').eq(0).text(), $(t).find('MCP_x').eq(0).text(), $(t).find('MCP_y').eq(0).text())  : 0 == $(t).find('BeState').eq(0).text() && g_oPreviewInstance.SetZeroEnLargeState($(t).find('BeState').eq(0).text(), $(t).find('MCP_x').eq(0).text(), $(t).find('MCP_y').eq(0).text())
}
function ZoomInfoCallback(e) {
    var t = g_oCommon.parseXmlFromStr(e),
    a = g_oCommon.m_lHttp + g_oCommon.m_szHostName + ':' + g_oCommon.m_lHttpPort + '/ISAPI/ContentMgmt/PTZCtrlProxy/channels/' + g_oPreviewInstance.m_iWndChannel[g_oPreviewInstance.m_iCurWnd] + '/set3DZoom';
    $.ajax({
        type: 'put',
        username: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [0],
        password: g_oWebSession.getUNamePWD(g_oCommon.m_szUserPwdValue) [1],
        url: a,
        processData: !1,
        data: t,
        success: function () {
        }
    })
}
var m_iPtzSpeed = 60;
