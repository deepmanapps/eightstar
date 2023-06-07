'use strict';
var KTSocialFeeds = (function () {
  var e = document.getElementById('kt_social_feeds_more_posts_btn'),
    t = document.getElementById('kt_social_feeds_more_posts'),
    n = document.getElementById('kt_social_feeds_posts'),
    o = document.getElementById('kt_social_feeds_post_input'),
    d = document.getElementById('kt_social_feeds_post_btn'),
    i = document.getElementById('kt_social_feeds_new_post');
  return {
    init: function () {
      e.addEventListener('click', function (n) {
        n.preventDefault(),
          e.setAttribute('data-kt-indicator', 'on'),
          (e.disabled = !0),
          setTimeout(function () {
            e.removeAttribute('data-kt-indicator'),
              (e.disabled = !1),
              e.classList.add('d-none'),
              t.classList.remove('d-none'),
              KTUtil.scrollTo(t, 200);
          }, 1e3);
      }),
        d.addEventListener('click', function (e) {
          e.preventDefault(),
            d.setAttribute('data-kt-indicator', 'on'),
            (d.disabled = !0),
            setTimeout(function () {
              d.removeAttribute('data-kt-indicator'), (d.disabled = !1);
              var e = o.value,
                t = i.querySelector('.card').cloneNode(!0);
              n.prepend(t), e.length > 0 && (t.querySelector('[data-kt-post-element="content"]').innerHTML = e), KTUtil.scrollTo(t, 200);
            }, 1e3);
        });
    },
  };
})();
KTUtil.onDOMContentLoaded(function () {
  KTSocialFeeds.init();
});
