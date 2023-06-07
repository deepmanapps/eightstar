'use strict';
var KTSupportCenterGeneral = {
  init: function () {
    !(function (e) {
      var t = e;
      if ((void 0 === t && (t = document.querySelectorAll('.highlight')), t && t.length > 0))
        for (var n = 0; n < t.length; ++n) {
          var r = t[n].querySelector('.highlight-copy');
          r &&
            new ClipboardJS(r, {
              target: function (e) {
                var t = e.closest('.highlight'),
                  n = t.querySelector('.tab-pane.active');
                return null == n && (n = t.querySelector('.highlight-code')), n;
              },
            }).on('success', function (e) {
              var t = e.trigger.innerHTML;
              (e.trigger.innerHTML = 'copied'),
                e.clearSelection(),
                setTimeout(function () {
                  e.trigger.innerHTML = t;
                }, 2e3);
            });
        }
    })();
  },
};
KTUtil.onDOMContentLoaded(function () {
  KTSupportCenterGeneral.init();
});
