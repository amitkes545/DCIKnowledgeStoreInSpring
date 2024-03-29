<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
    <head>
        <title>Screen Sharing</title>
        <script type="text/javascript" src="../ScreenSharing/JS/jquery-2.1.3.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
        <link rel="author" type="text/html" href="https://plus.google.com/+MuazKhan">
        <meta name="author" content="Muaz Khan">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        
        <link rel="stylesheet" href="//cdn.webrtc-experiment.com/style.css">
        
        <style>
            video {
                -moz-transition: all 1s ease;
                -ms-transition: all 1s ease;
                
                -o-transition: all 1s ease;
                -webkit-transition: all 1s ease;
                transition: all 1s ease;
                vertical-align: top;
                width: 100%;
            }

            input {
                border: 1px solid #d9d9d9;
                border-radius: 1px;
                font-size: 2em;
                margin: .2em;
                width: 30%;
            }

            select {
                border: 1px solid #d9d9d9;
                border-radius: 1px;
                height: 50px;
                margin-left: 1em;
                margin-right: -12px;
                padding: 1.1em;
                vertical-align: 6px;
                width: 18%;
            }

            .setup {
                border-bottom-left-radius: 0;
                border-top-left-radius: 0;
                font-size: 102%;
                height: 47px;
                margin-left: -9px;
                margin-top: 8px;
                position: absolute;
            }

            p { padding: 1em; }

            li {
                border-bottom: 1px solid rgb(189, 189, 189);
                border-left: 1px solid rgb(189, 189, 189);
                padding: .5em;
            }
        </style>
        <script>
            document.createElement('article');
            document.createElement('footer');
        </script>
        
        <link rel="chrome-webstore-item" href="https://chrome.google.com/webstore/detail/ajhifddimkapgcifgcodmmfdlknahffk">
        
        <!-- scripts used for screen-sharing -->
        <script src='//cdn.webrtc-experiment.com/firebase.js'> </script>
        <script src="//cdn.webrtc-experiment.com/Pluginfree-Screen-Sharing/conference.js"> </script>
    </head>

    <body>
        <article>
            <header style="text-align: center;">
                
            </header>

            <!-- just copy this <section> and next script -->
            <section class="experiment">                
                <section>
                    
                    <input type="text" value="sandeep"  id="room-name" placeholder="Your Name" disabled>
                    <button id="share-screen" class="setup" disabled>Share</button>
                </section>
                
               
            <script>
                // Muaz Khan     - https://github.com/muaz-khan
                // MIT License   - https://www.webrtc-experiment.com/licence/
                // Documentation - https://github.com/muaz-khan/WebRTC-Experiment/tree/master/Pluginfree-Screen-Sharing
                
                var isWebRTCExperimentsDomain = document.domain.indexOf('webrtc-experiment.com') != -1;

                var config = {
                    openSocket: function(config) {
                        var channel = config.channel || 'screen-capturing-' + location.href.replace( /\/|:|#|%|\.|\[|\]/g , '');
                        var socket = new Firebase('https://webrtc.firebaseIO.com/' + channel);
                        socket.channel = channel;
                        socket.on("child_added", function(data) {
                            config.onmessage && config.onmessage(data.val());
                        });
                        socket.send = function(data) {
                            this.push(data);
                        };
                        config.onopen && setTimeout(config.onopen, 1);
                        socket.onDisconnect().remove();
                        return socket;
                    },
                    onRemoteStream: function(media) {
                        var video = media.video;
                        video.setAttribute('controls', true);
                        videosContainer.insertBefore(video, videosContainer.firstChild);
                        video.play();
                        rotateVideo(video);
                    },
                    onRoomFound: function(room) {
                        if(location.hash.replace('#', '').length) {
                            // private rooms should auto be joined.
                            conferenceUI.joinRoom({
                                roomToken: room.roomToken,
                                joinUser: room.broadcaster
                            });
                            return;
                        }
                        
                        var alreadyExist = document.getElementById(room.broadcaster);
                        if (alreadyExist) return;

                        if (typeof roomsList === 'undefined') roomsList = document.body;

                        var tr = document.createElement('tr');
                        tr.setAttribute('id', room.broadcaster);
                        tr.innerHTML = '<td>' + room.roomName + '</td>' +
                            '<td><button class="join" id="' + room.roomToken + '">Open Screen</button></td>';
                        roomsList.insertBefore(tr, roomsList.firstChild);

                        var button = tr.querySelector('.join');
                        button.onclick = function() {
                            var button = this;
                            button.disabled = true;
                            conferenceUI.joinRoom({
                                roomToken: button.id,
                                joinUser: button.parentNode.parentNode.id
                            });
                        };
                    },
                    onNewParticipant: function(numberOfParticipants) {
                        document.title = numberOfParticipants + ' users are viewing your screen!';
                        var element = document.getElementById('number-of-participants');
                        if (element) {
                            element.innerHTML = numberOfParticipants + ' users are viewing your screen!';
                        }
                    },
                    oniceconnectionstatechange: function(state) {
                        if(state == 'failed') {
                            alert('Failed to bypass Firewall rules. It seems that target user did not receive your screen. Please ask him reload the page and try again.');
                        }
                        
                        if(state == 'connected') {
                            alert('A user successfully received your screen.');
                        }
                    }
                };

                function captureUserMedia(callback, extensionAvailable) {
                    console.log('captureUserMedia chromeMediaSource', DetectRTC.screen.chromeMediaSource);
                    
                    var screen_constraints = {
                        mandatory: {
                            chromeMediaSource: DetectRTC.screen.chromeMediaSource,
                            maxWidth: screen.width > 1920 ? screen.width : 1920,
                            maxHeight: screen.height > 1080 ? screen.height : 1080
                            // minAspectRatio: 1.77
                        },
                        optional: [{ // non-official Google-only optional constraints
                            googTemporalLayeredScreencast: true
                        }, {
                            googLeakyBucket: true
                        }]
                    };

                    // try to check if extension is installed.
                    if(isChrome && isWebRTCExperimentsDomain && typeof extensionAvailable == 'undefined' && DetectRTC.screen.chromeMediaSource != 'desktop') {
                        DetectRTC.screen.isChromeExtensionAvailable(function(available) {
                            captureUserMedia(callback, available);
                        });
                        return;
                    }
                    
                    if(isChrome && isWebRTCExperimentsDomain && DetectRTC.screen.chromeMediaSource == 'desktop' && !DetectRTC.screen.sourceId) {
                        DetectRTC.screen.getSourceId(function(error) {
                            if(error && error == 'PermissionDeniedError') {
                                alert('PermissionDeniedError: User denied to share content of his screen.');
                            }
                            
                            captureUserMedia(callback);
                        });
                        return;
                    }
                    
                    // for non-www.webrtc-experiment.com domains
                    if(isChrome && !isWebRTCExperimentsDomain && !DetectRTC.screen.sourceId) {
                        window.addEventListener('message', function (event) {
                            if (event.data && event.data.chromeMediaSourceId) {
                                var sourceId = event.data.chromeMediaSourceId;

                                DetectRTC.screen.sourceId = sourceId;
                                DetectRTC.screen.chromeMediaSource = 'desktop';

                                if (sourceId == 'PermissionDeniedError') {
                                    return alert('User denied to share content of his screen.');
                                }

                                captureUserMedia(callback, true);
                            }

                            if (event.data && event.data.chromeExtensionStatus) {
                                warn('Screen capturing extension status is:', event.data.chromeExtensionStatus);
                                DetectRTC.screen.chromeMediaSource = 'screen';
                                captureUserMedia(callback, true);
                            }
                        });
                        screenFrame.postMessage();
                        return;
                    }
                    
                    if(isChrome && DetectRTC.screen.chromeMediaSource == 'desktop') {
                        screen_constraints.mandatory.chromeMediaSourceId = DetectRTC.screen.sourceId;
                    }
                    
                    var constraints = {
                        audio: false,
                        video: screen_constraints
                    };
                    
                    if(!!navigator.mozGetUserMedia) {
                        console.warn(Firefox_Screen_Capturing_Warning);
                        constraints.video = {
                            mozMediaSource: 'window',
                            mediaSource: 'window',
                            maxWidth: 1920,
                            maxHeight: 1080,
                            minAspectRatio: 1.77
                        };
                    }
                    
                    console.log( JSON.stringify( constraints , null, '\t') );
                    
                    var video = document.createElement('video');
                    video.setAttribute('autoplay', true);
                    video.setAttribute('controls', true);
                    videosContainer.insertBefore(video, videosContainer.firstChild);
                    
                    getUserMedia({
                        video: video,
                        constraints: constraints,
                        onsuccess: function(stream) {
                            config.attachStream = stream;
                            callback && callback();

                            video.setAttribute('muted', true);
                            rotateVideo(video);
                        },
                        onerror: function() {
                            if (isChrome && location.protocol === 'http:') {
                                alert('Please test this WebRTC experiment on HTTPS.');
                            } else if(isChrome) {
                                alert('Screen capturing is either denied or not supported. Please install chrome extension for screen capturing or run chrome with command-line flag: --enable-usermedia-screen-capturing');
                            }
                            else if(!!navigator.mozGetUserMedia) {
                                alert(Firefox_Screen_Capturing_Warning);
                            }
                        }
                    });
                }

                /* on page load: get public rooms */
                var conferenceUI = conference(config);

                /* UI specific */
                var videosContainer = document.getElementById("videos-container") || document.body;
                var roomsList = document.getElementById('rooms-list');

                document.getElementById('share-screen').onclick = function() {
                    var roomName = document.getElementById('room-name') || { };
                    roomName.disabled = true;
                    captureUserMedia(function() {
                        conferenceUI.createRoom({
                            roomName: (roomName.value || 'Anonymous') + ' shared his screen with you'
                        });
                    });
                    this.disabled = true;
                };

                function rotateVideo(video) {
                    video.style[navigator.mozGetUserMedia ? 'transform' : '-webkit-transform'] = 'rotate(0deg)';
                    setTimeout(function() {
                        video.style[navigator.mozGetUserMedia ? 'transform' : '-webkit-transform'] = 'rotate(360deg)';
                    }, 1000);
                }

                (function() {
                    var uniqueToken = document.getElementById('unique-token');
                    if (uniqueToken)
                        if (location.hash.length > 2) uniqueToken.parentNode.parentNode.parentNode.innerHTML = '<h2 style="text-align:center;"><a href="' + location.href + '" target="_blank">Share this link</a></h2>';
                        else uniqueToken.innerHTML = uniqueToken.parentNode.parentNode.href = '#' + (Math.random() * new Date().getTime()).toString(36).toUpperCase().replace( /\./g , '-');
                })();
                
                var Firefox_Screen_Capturing_Warning = 'Make sure that you are using Firefox Nightly and you enabled: media.getusermedia.screensharing.enabled flag from about:config page. You also need to add your domain in "media.getusermedia.screensharing.allowed_domains" flag.';

            </script>
            
            <script>
                var screenFrame, loadedScreenFrame;
                
                function loadScreenFrame(skip) {
                    if(loadedScreenFrame) return;
                    if(!skip) return loadScreenFrame(true);

                    loadedScreenFrame = true;

                    var iframe = document.createElement('iframe');
                    iframe.onload = function () {
                        iframe.isLoaded = true;
                        console.log('Screen Capturing frame is loaded.');
                        
                        document.getElementById('share-screen').disabled = false;
                        document.getElementById('room-name').disabled = false;
                    };
                    iframe.src = 'https://www.webrtc-experiment.com/getSourceId/';
                    iframe.style.display = 'none';
                    (document.body || document.documentElement).appendChild(iframe);

                    screenFrame = {
                        postMessage: function () {
                            if (!iframe.isLoaded) {
                                setTimeout(screenFrame.postMessage, 100);
                                return;
                            }
                            console.log('Asking iframe for sourceId.');
                            iframe.contentWindow.postMessage({
                                captureSourceId: true
                            }, '*');
                        }
                    };
                };
                
                if(!isWebRTCExperimentsDomain) {
                    loadScreenFrame();
                }
                else {
                    document.getElementById('share-screen').disabled = false;
                    document.getElementById('room-name').disabled = false;
                }
            </script>
            
          
        <!-- commits.js is useless for you! -->
        <script src="//cdn.webrtc-experiment.com/commits.js" async> </script>
    </body>
</html>