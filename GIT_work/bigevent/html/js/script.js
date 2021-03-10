var script = function() {

    var $body = $('html,body');
    var winH = $(window).height();
    var winW = $(window).outerWidth();
    var $win = $(window);
    var scrollBottom = $(window).scrollTop() + $(window).height();
    var conH = $('.wrapper').height();
    var headerH = $('.myheader2').height();

    var $sec = $('.sec');
    var hash = location.hash,
        href;

    /* page scroll */
    var thispage = 0;
    var pagescroll = true;
    var pageT = [],
        $sec = $('.sec'),
        $main = $('.wrapper'),
        // _mainTOP = $main.offset().top,
        ah = $main.outerHeight(true),
        pageName = ['back2020', 'listen2020', 'top2020', 'sad2020', 'ten', 'hello2021', 'power2021'];

    var _thispagescroll = pageName.indexOf(hash);

    var first_tr = $('.first_tr');
    var cover_tr = $('.cover_tr');
    var first_skip = $('.first .skip');


    $(document).ready(function() {

        if (winW >= 769) {
            counter();
            setTimeout(function() {
                first_tr.attr('src', 'images/title/title.html');
            }, 5500);

            TweenMax.to(first_skip, 5, { autoAlpha: 1, delay: 9, ease: Linear.easeNone });
            TweenMax.to(first_skip, 6, { css: { zIndex: 999 }, delay: 9, ease: Linear.easeNone });
        } else {

            counter();
            setTimeout(function() {
                first_tr.attr('src', 'images/title_m/title_m.html');
            }, 5500);

            TweenMax.to(first_skip, 5, { autoAlpha: 1, delay: 9, ease: Linear.easeNone });
            TweenMax.to(first_skip, 6, { css: { zIndex: 999 }, delay: 9, ease: Linear.easeNone });
        }


        function counter() {

            $('.counter').fadeIn(1000).delay(500);
            // TweenMax.to('.counter', 1, { autoAlpha: 1, delay: 1, ease: Linear.easeNone });
            TweenMax.to('.counter .load2', 1, { autoAlpha: 1, delay: 1, ease: Linear.easeNone });
            TweenMax.to('.counter .load', 1, { autoAlpha: 1, delay: 1, ease: Linear.easeNone });
            var counter = 0;
            var c = 0;
            var i = setInterval(function() {
                $('.load').css("width", c + "%");

                counter++;
                c++;

                if (counter == 101) {
                    clearInterval(i);
                }

            }, 50);

            TweenMax.to('.counter', 1, { autoAlpha: 0, delay: 6, ease: Linear.easeNone });
        }

    });



    var mouseScroll = $('.mouseScroll');

    function introOut() {
        $('.skip').attr('toggle', '1');
        $('.navbtn.home').removeClass('select');
        $('.navbtn[data-page="' + 0 + '"]').addClass('select');

        $('.kv .first').fadeOut(1000).delay(500);

        first_tr.attr('src', '');
        if (winW >= 769) {

            cover_tr.attr('src', 'images/kv_bg/kv_bg2.html');
        }

        var mouseani = new TimelineMax();
        mouseani.to(mouseScroll, .5, { autoAlpha: 1, delay: 1, ease: Linear.easeNone })
            .to(mouseScroll, .5, { autoAlpha: 0, delay: 3, ease: Linear.easeNone })
            .to(mouseScroll, .5, { css: { zIndex: -1 }, ease: Linear.easeNone });

    }

    first_skip.click(introOut);


    var tabs = $('.tabs');

    $('.mybook .open,.mybook .ar').on('click', function() {

        var navopen = $('.pick').attr('toggle');

        if (navopen == 0) {

            tabs.slideToggle();
            tabs.css('z-index', '20');

            $('.sec2 .black').css({
                'z-index': '20',
                'opacity': '1'
            })

            $('.sec2 .pick').css({

                'z-index': '20',
                'opacity': '1'
            })


            $('.pick').attr('toggle', '1');

        } else {

            tabs.slideToggle();
            tabs.css('z-index', '1');

            $('.sec2 .black').css({

                'z-index': '-1',
                'opacity': '0'
            })

            $('.sec2 .pick').css({

                'z-index': '1',
                'opacity': '1'
            })

            $('.pick').attr('toggle', '0');
        }
    });


    $('.mybook .goto').on('click', function() {

        if ($(this).hasClass('go')) return false;

        var oldgo = $('.mybook .goto.go').data('to');

        var thisgo = $(this).data('to');

        var thistext = $(this).text();

        var sec2 = $('.sec2').offset().top;


        if (oldgo > thisgo) {
            moveback();
        } else {
            movenext();
        }

        setTimeout(function() {

            $('.mybook .goto.go').removeClass('go');

            $('.goto[data-to="' + thisgo + '"]').addClass('go');

            $('.mybook .book.go').removeClass('go');

            $('.book[data-go="' + thisgo + '"]').addClass('go');

        }, 1000);

        $('html,body').animate({ scrollTop: sec2 - headerH }, 100);

        tabs.slideToggle();
        tabs.css('z-index', '1');

        $('.open').text(thistext);

        $('.sec2 .black').css({
            'z-index': '-1',
            'opacity': '0'
        })

        $('.sec2 .pick').css({
            'z-index': '1',
            'opacity': '1'
        })

        $('.pick').attr('toggle', '0');

    });

    $('.mybook .left').on('click', function() {

        var thisbook = $('.mybook .book.go').data('go');

        if (thisbook == 0) {
            return false;
        } else {
            var back = 0;
            back = (thisbook - 1);
        }

        var backtext = $('.goto[data-to="' + back + '"]').text();

        setTimeout(function() {
            $('.book[data-go="' + thisbook + '"]').removeClass('go');
            $('.book[data-go="' + back + '"]').addClass('go');
        }, 1000);

        setTimeout(function() {
            $('.mybook .goto.go').removeClass('go');
            $('.goto[data-to="' + back + '"]').addClass('go');
        }, 1000);

        moveback();

        $('.open').text(backtext);

    });


    $('.mybook .right').on('click', function() {

        var thisbook = $('.mybook .book.go').data('go');
        var bookl = $('.mybook .book').length;
        var next = thisbook + 1;
        var end = bookl - 1;

        var nexttext = $('.goto[data-to="' + next + '"]').text();

        if (thisbook == end) {
            return false;
        } else {
            setTimeout(function() {
                $('.book[data-go="' + thisbook + '"]').removeClass('go');
                $('.book[data-go="' + next + '"]').addClass('go');
            }, 1000);
            setTimeout(function() {
                $('.mybook .goto.go').removeClass('go');
                $('.goto[data-to="' + next + '"]').addClass('go');
                // $('.booklist .icon_group').fadeIn(1000).delay(500);
            }, 1000);
            movenext();
        }

        $('.open').text(nexttext);
    });

    var movenext = function() {

        if (winW <= 768) {
            $('.move_n').attr('src', '');
            $('.move_n').attr('src', 'images/backward_m/backward_m.gif');
            $('.page_move').fadeIn(1000).delay(500).fadeOut(1000);

        } else {
            $('.move_n').attr('src', '');
            $('.move_n').attr('src', 'images/backward/backward.gif');
            $('.page_move').fadeIn(1000).delay(500).fadeOut(1000);
            $('.booklist .icon_group').fadeOut(1000).delay(500).fadeIn(1000);
            $('.booklist .more').fadeOut(1000).delay(500).fadeIn(1000);
        }

    }

    var moveback = function() {
        if (winW <= 768) {
            $('.move_b').attr('src', '');
            $('.move_b').attr('src', 'images/forward_m/forward_m.gif');
            $('.page_move2').fadeIn(1000).delay(500).fadeOut(1000);

        } else {
            $('.move_b').attr('src', '');
            $('.move_b').attr('src', 'images/forward/forward.gif');
            $('.page_move2').fadeIn(1000).delay(500).fadeOut(1000);
            $('.booklist .icon_group').fadeOut(1000).delay(500).fadeIn(1000);
            $('.booklist .more').fadeOut(1000).delay(500).fadeIn(1000);
        }
    }


    $('#newslistWrap').scroll(function() {

        var now = $('#news li.top').data('go');

        var height = $('#news li.top').height();

        var thisoffset = $('.new_li[data-go="' + now + '"]').position().top - 110;

        if (thisoffset < 60) {

            if ((-thisoffset) >= height) {

                next = now + 1;

                $('#news li.top').removeClass('top');
                $('.new_li[data-go="' + next + '"]').addClass('top');

                $('#month li.selected').removeClass('selected');
                $('#month li[data-id="' + next + '"]').addClass('selected');

                var _height2 = $('#month').scrollTop();

                if (winW > 768) {

                    $('#month').animate({
                        scrollTop: _height2 + 150
                    }, 600);

                } else {

                    $('#month').animate({
                        scrollTop: _height2 + 50
                    }, 600);

                }
            }

        } else {

            next = now - 1;

            $('#news li.top').removeClass('top');
            $('.new_li[data-go="' + next + '"]').addClass('top');

            $('#month li.selected').removeClass('selected');
            $('#month li[data-id="' + next + '"]').addClass('selected');

            var _height2 = $('#month').scrollTop();

            if (winW > 768) {

                $('#month').animate({
                    scrollTop: _height2 - 150
                }, 600);

            } else {

                $('#month').animate({
                    scrollTop: _height2 - 50
                }, 600);

            }
        }
    });


    $('#month li').on('click', function() {

        if ($(this).hasClass('selected')) return false;
        $('#month li.selected').removeClass('selected');
        var oldmonth = $('#month li.selected').data('id');
        var thisgo = $(this).data('id');
        var thisoffset = $('.new_li[data-go="' + thisgo + '"]').offset().top;
        var _height = $('#newslistWrap').scrollTop();

        var nextoffset = _height + thisoffset;

        $('#month li[data-id="' + thisgo + '"]').addClass('selected');


        if (winW > 768) {

            $('#newslistWrap').animate({
                scrollTop: nextoffset - 50
            }, 600);

        } else {
            $('#newslistWrap').animate({
                scrollTop: nextoffset - 50
            }, 600);

        }

    });


    var timeline = $('#timeline');


    if (winW > 768) {
        // PC版
        timeline.timelinr({
            containerDiv: '#timeline',
            datesDiv: '#year',
            issuesDiv: '#people',
            orientation: 'vertical',
            issuesSpeed: 300,
            datesSpeed: 100,
            arrowKeys: 'false',
            autoPlay: 'false',
            mousewheel: 'true',
            swipe: 'true',
            issuesTransparency: 0.2,
            startAt: 4
        });


        timeline.on('wheel', (function(e) {

            e.preventDefault();
            sec2 = $('.sec2').offset().top;
            sec3 = $('.sec3').offset().top;
            sec6 = $('.sec6').offset().top;
            var newT = $('#people').offset().top;
            var timeline = $('#timeline').offset().top;
            var length = $('#people li').length;
            var height = $('#people li').height();
            var end = (length - 1) * height;
            var down = timeline - end;


            if (e.originalEvent.deltaY < 0) {
                if (newT == timeline) {
                    $('html,body').animate({ scrollTop: sec2 - headerH }, 100);
                } else {
                    $('#prev').click();
                    $('html,body').animate({ scrollTop: sec3 - headerH }, 100);
                }
            } else {
                if (newT <= down) {
                    $('html,body').animate({ scrollTop: sec6 - headerH }, 100);
                } else {
                    $('#next').click();
                    $('html,body').animate({ scrollTop: sec3 - headerH }, 100);
                }
            }

        }));

    } else {
        // 手機版
        $('#year').slick({
            slidesToShow: 7,
            slidesToScroll: 1,
            arrows: false,
            dots: false,
            verticalSwiping: true,
            vertical: true,
            centerMode: true,
            draggable: false,
            focusOnSelect: true,
            initialSlide: 3,
            infinite: true,
            touchMove: true,
            swipeToSlide: true,
            asNavFor: '#people'
        });

        $('#people').slick({
            slidesToShow: 1,
            slidesToScroll: 1,
            arrows: false,
            dots: false,
            initialSlide: 3,
            verticalSwiping: true,
            touchMove: true,
            draggable: true,
            vertical: true,
            swipeToSlide: true,
            focusOnSelect: true,
            infinite: true,
            asNavFor: '#year'
        });
    }

    $('.coming').on('click', function() {
        $('.animal.coming').addClass('soon');
    });

    var _sec3pos = $('.sec3').offset().top;

    var _sec5pos = $('.sec5').offset().top;

    function headerfix() {

        var _scroll = $win.scrollTop();

        if (_scroll >= 200) {
            $('.myheader2').fadeIn('fast');
            $('.sec5 .sectitle').css('opacity', 1);
        } else {
            $('.myheader2').fadeOut('fast');

        }

    }

    $win.scroll(headerfix);


    function podcast() {

        var _sec1pos = $('.sec1').offset().top;
        var _scroll = $win.scrollTop();


        if (_scroll >= _sec1pos) {

            if (_scroll > _sec1pos + 350) {

                var lbname = $('.lb2.popen').data('lb');
                lbchange(lbname);

            }

        } else {
            if (_scroll < _sec1pos - 500) {

                var lbname = $('.lb2.popen').data('lb');
                lbchange(lbname);

            }
        }

    }

    $win.scroll(podcast);



    $(window).scroll(function() {

        last = $('body').height() - $(window).height() - 400;

        var _scroll = $win.scrollTop();

        if ($(window).scrollTop() >= last) {
            $('.down').addClass('turn');
        } else {
            $('.down').removeClass('turn');
        }

        if (_scroll < _sec5pos - 350) {

            if (_scroll >= _sec3pos - 100) {
                $('.up').css('display', 'block');
            } else {

                $('.up').css('display', 'none');
            }

        } else {

            $('.up').css('display', 'none');
        }

    })


    $('.up').on('click', function() {

        var nextpage = thispage - 1;

        $body.animate({
            scrollTop: pageT[nextpage] - headerH
        }, 600);
    });


    $('.down').on('click', function() {

        var nextpage = thispage + 1;

        if (thispage == 6) {
            $('html,body').animate({ scrollTop: 0 }, 333);
            $('.down.turn').removeClass('turn');
        } else {

            $body.animate({
                scrollTop: pageT[nextpage] - headerH
            }, 600);
        }

    });


    $('.sec5 .go').click(formspin);


    function formspin() {

        var lbAni = new TimelineMax();
        lbAni.to($('.draw'), .5, { css: { zIndex: 10 } }, '-=.5')
            .to($('.draw'), .5, { autoAlpha: 1, ease: Linear.easeInOut });

        $body.css({
            'overflow': 'hidden'
        })

        setTimeout(function() {

            var lbCloseAni = new TimelineMax();
            lbCloseAni.to($('.draw'), .3, { opacity: 0, ease: Linear.easeNone })
                .to($('.draw'), .3, { css: { zIndex: -1 } });

            $body.css({
                'overflow': ''
            })

        }, 3000)

        setTimeout(function() {

            lbOpen('lucky');

            $('.before').attr('src', '');
            $('.before').attr('src', 'images/mom/fortune.gif');

            TweenMax.to('.lucky .after', 2, { autoAlpha: 1, delay: 1, ease: Linear.easeInOut });
            TweenMax.to('.lucky .before', 4, { opacity: 0, delay: 2, ease: Linear.easeInOut });

        }, 3000)
    }


    // 主選單

    function navde() {

        $('.burger').attr('toggle', '0');
        $('.burger').removeClass('isclick');

        $('.myheader .box2').css('height', '');
        TweenMax.to('.myheader', .1, { backgroundColor: '', ease: Quart.easeOut });

        $('body').css({
            'overflow-y': '',
            'position': '',
            'height': ''
        });

        $('.logo1').css({
            'display': 'block'
        });

        if (winW <= 768) {
            $('.box2').hide();
        } else {
            $('.box2').show();
        }
    }
    navde();

    // 主選單
    function navfunc() {
        var navopen = $('.burger').attr('toggle');
        if (navopen == 0) {

            $('.myheader').css({
                'height': '',
                'overflow-y': 'scroll'
            });

            TweenMax.to('.myheader', .1, { backgroundColor: '#000', ease: Quart.easeOut });
            $('.box2').slideDown();


            $('body').css({
                'overflow-y': 'hidden',
                'position': 'fixed',
                'height': '100%'
            });

            $('.logo1').css({
                'display': 'none'
            });

            $('.burger').attr('toggle', '1');
            $('.burger').addClass('isclick');

        } else if (navopen == 1) {

            $('.box2').css('height', '');
            TweenMax.to('.myheader', .1, { backgroundColor: 'transparent', delay: .3, ease: Quart.easeOut });
            $('.box2').slideUp();


            $('body').css({
                'overflow-y': '',
                'position': '',
                'height': ''
            });

            $('.logo1').css({
                'display': 'block'
            });

            $('.burger').attr('toggle', '0');
            $('.burger').removeClass('isclick');
        }
    }
    $('.burger').click(navfunc);

    /* nav scroll --------------------------------------------*/

    /* page scroll */
    var thispage = 0;
    var pagescroll = true;
    var pageT = [],
        $sec = $('.sec'),
        $main = $('.wrapper'),
        ah = $main.outerHeight(true);


    var $navbtn = $('.navbtn');



    var pageReset = function() {
        pageT = [];
        winW = $win.outerWidth();
        ah = $main.outerHeight(true);
        $sec.each(
            function(i, obj) {
                var t = $(obj).offset().top;
                pageT.push(t);
                if (i == $sec.length - 1) { pageT.push(ah); }
            });
    }

    var menuselect = function() {
        $navbtn.removeClass('select');

        pageReset();

        $('.navbtn[data-page="' + thispage + '"]').addClass('select');
    }

    var menuscroll = function(n) {
        var _pos;
        thispage = n;

        pageReset();

        $body.animate({
            scrollTop: pageT[thispage] - headerH
        }, 600);
    }

    var scrollPage = function(wt, whelf) {
        winH = $win.height();
        winW = $win.outerWidth();
        wt = $win.scrollTop();
        whelf = winH / 3;

        for (var p = 0; p < $sec.length; p++) {
            if (wt > pageT[p] - whelf && wt < pageT[p + 1] - whelf) {
                thispage = p;
            }
        }

        menuselect();

        href = pageName[thispage];

        // 平時的方式
        // window.location.hash = href;


        // 瀏覽器歷史紀錄的方式
        history.pushState(null, "", '?page=' + href);
    }


    var scrollthispage = function() {

        // 平時的方式
        // var _pos;
        // _thispagescroll = pageName.indexOf(hash.substr(1));

        // 取得目前網址
        _thispagescroll = pageName.indexOf(getPage());
        console.log(thispage);


        pageReset();

        var topH = $('.myheader2').height();

        if (_thispagescroll != -1) {

            // 平時的方式
            // thispage = pageName.indexOf(hash.substr(1));

            // 取得目前網址
            thispage = pageName.indexOf(getPage());
            console.log(thispage);

            var skipbtn = $('.skip').attr('toggle');

            pageReset();

            if (skipbtn == 1) {

                if (thispage == 0) {
                    TweenMax.to('body,html', .8, { scrollTop: 0, ease: Quart.easeOut });
                } else {
                    TweenMax.to('body,html', .8, { scrollTop: pageT[thispage] - topH, ease: Quart.easeOut });
                }

            } else {

                $('.navbtn.home').addClass('select');
            }

            // 平時的方式
            // href = pageName[thispage];
            // window.location.hash = href;
            // pagescroll = false;

            menuselect();

            // 瀏覽器歷史紀錄的方式
            href = pageName[thispage];
            history.pushState(null, "", '?page=' + href);


        } else {
            TweenMax.to('body,html', .8, { scrollTop: 0, ease: Quart.easeOut });

            // 瀏覽器歷史紀錄的方式
            menuselect();
            href = '';
        };

    }

    // 取得目前網址
    function getPage() {
        var page = '';
        var search = location.search.substr(1).split('&');
        $.each(search, function(i) {
            var param = search[i].substr(0, 4); //page
            var val = search[i].substr(5); //第5個參數之後的，也就是=之後的
            if (param == 'page' && pageName.indexOf(val) >= 0)
                page = val;
        });

        return page;
    }

    setTimeout(function() {
        scrollthispage()
    }, 600);



    $navbtn.click(
        function() {
            var n = $(this).data('page');
            menuscroll(n);
            navde();
        }
    );


    /* lightbox
    ------------------------------------------------------------*/



    var $lb = $('.lb'),
        $lbbox = $('.lbbox');


    var lb_data;


    var lbOpen = function(lb_data) {

        var lbAni = new TimelineMax();
        lbAni.to($('.lb[data-lb="' + lb_data + '"]'), .5, { css: { zIndex: 999 } }, '-=.5')
            .to($('.lb[data-lb="' + lb_data + '"]'), .5, { autoAlpha: 1, ease: Linear.easeNone });

        $body.css({
            'overflow': 'hidden'
            // 'position': 'fixed'
        })
    }

    var lbOpen2 = function(lb_data) {

        var lbAni = new TimelineMax();
        lbAni.to($('.lb2[data-lb="' + lb_data + '"]'), .5, { css: { zIndex: 999 } }, '-=.5')
            .to($('.lb2[data-lb="' + lb_data + '"]'), .5, { autoAlpha: 1, ease: Linear.easeNone });

        // $body.css({
        //     'overflow': ''
        //     // 'position': 'fixed'
        // })
    }

    var lbClose = function(lb_data) {

        var lbCloseAni = new TimelineMax();
        lbCloseAni.to($('.lb'), .5, { autoAlpha: 0, ease: Linear.easeNone })
            .to($('.lb'), .5, { css: { zIndex: -1 } });


        var lbCloseAni = new TimelineMax();
        lbCloseAni.to($('.lb2[data-lb="' + lb_data + '"]'), .5, { autoAlpha: 0, ease: Linear.easeNone })
            .to($('.lb2[data-lb="' + lb_data + '"]'), .5, { css: { zIndex: -1 } });


        $('.lb2').removeClass('popen');


        $body.css({
            'overflow': ''
            // 'position': 'fixed'
        })

        TweenMax.to('.lucky .after', 2, { opacity: 0, delay: 1, ease: Linear.easeInOut });
        TweenMax.to('.lucky .before', 2, { autoAlpha: 1, delay: 1, ease: Linear.easeInOut });
    }

    var lbClose2 = function(lb_data) {

        $('.podcast iframe').attr('src', '');

        var lbCloseAni = new TimelineMax();
        lbCloseAni.to($('.lb2'), .1, { autoAlpha: 0, ease: Linear.easeNone })
            .to($('.lb2'), .1, { css: { zIndex: -1 } });

        // $body.css({
        //     'overflow': ''
        //     // 'position': 'fixed'
        // })


        setTimeout(function() {

            $('.lb2').removeClass('pdown');
            $('.lb2').removeClass('popen');

        }, 200)


    }

    $('.lb').click(function(e) {
        var _con = $('.lbbox'); // 设置目标区域
        if (!_con.is(e.target) && _con.has(e.target).length === 0) { // Mark 1
            lbClose();
        }
    });

    var lbchange = function(lb_data) {

        $('.lb2[data-lb="' + lb_data + '"]').addClass('pdown');
    }



    // $('.lb2').click(function(e) {

    //     var _con = $('.lbbox2'); // 设置目标区域
    //     if (!_con.is(e.target) && _con.has(e.target).length === 0) { // Mark 1

    //         var lbname = $(this).data('lb');
    //         lbchange(lbname);
    //     }
    // });


    $('.article.p1').click(function() {
        var lbname = $(this).data('hello_1');
        lbOpen('hello_1');
    });

    $('.article.p2').click(function() {
        var lbname = $(this).data('hello_2');
        lbOpen('hello_2');

    });

    $('.more').click(function() {
        var lbname = $(this).data('article_1');
        lbOpen('article_1');
    });

    $('.Compared').click(function() {
        var lbname = $(this).data('compared');
        lbOpen('compared');
    });


    $('.audio .play').click(function() {
        var lbname = $(this).data('lb');
        var pdown = $('.lb2.popen').data('lb');

        $('.lb2').removeClass('pdown');
        lbClose(pdown);

        if (lbname == "podcast1") {
            $('.podcast iframe').attr('src', 'https://player.soundon.fm/embed?podcast=baf7588a-ed23-411f-9422-d58b0a2bbd25&episode=7ce148c2-3cd9-4e1d-9052-692c5d4ec60e');
            $('.lb2[data-lb="' + lbname + '"]').addClass('popen');
            lbOpen2(lbname);


        } else if (lbname == "podcast2") {
            $('.podcast iframe').attr('src', 'https://player.soundon.fm/embed?podcast=baf7588a-ed23-411f-9422-d58b0a2bbd25&episode=ecd2a84b-2932-4484-905c-7e5b45b3b40e');
            $('.lb2[data-lb="' + lbname + '"]').addClass('popen');
            lbOpen2(lbname);

        } else {
            $('.podcast iframe').attr('src', 'https://player.soundon.fm/embed?podcast=baf7588a-ed23-411f-9422-d58b0a2bbd25&episode=b9c16528-0023-4996-9064-e25d8ba6fa99');
            $('.lb2[data-lb="' + lbname + '"]').addClass('popen');
            lbOpen2(lbname);

        }

    });


    $('.lb .btn_x').click(lbClose);

    $('.lb2 .btn_x').click(lbClose2);


    $('#people  .play').on('click', playThis);

    $('#news .play').on('click', playThis);

    function playThis() {
        var lbname = $(this).data('article_2');
        lbOpen('article_2');
    }

    /* resize
     ------------------------------------------------------------*/

    var _resize = function() {

        winW = $win.width();
        winH = $win.height();
        docH = $(document).height();
        _scroll = $(window).scrollTop();
        scrollBottom = $(window).scrollTop() + $(window).height();
        headerH = $('.myheader2').height();


        // _sec1pos = $('.sec1').offset().top;

        navde();
        // if (pagescroll || winW > 1000) scrollthispage();

        // navfix();

    }
    $win.scroll(scrollPage);
    _resize();
    $win.resize(function() {

        // _resize();

    });


    var windowHeight = $(window).height();

    // Resize Event
    $(window).resize(function() {

        // Check window width has actually changed and it's not just iOS triggering a resize event on scroll
        if ($(window).width() != windowHeight) {

            // Update the window width for next time
            windowHeight = $(window).width();

            // Do stuff here
            _resize();
            // if (pagescroll || windowHeight > 768) scrollthispage();
        }
    });

}




$(function() {

    var imagesLoaded = loading();
    imagesLoaded.loadfunc();
    imagesLoaded.callback = function() {

        script();

    };

});