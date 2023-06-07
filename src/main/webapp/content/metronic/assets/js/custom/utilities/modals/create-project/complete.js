'use strict';
var KTModalCreateProjectComplete = (function () {
  var e;
  return {
    init: function () {
      KTModalCreateProject.getForm(),
        (e = KTModalCreateProject.getStepperObj()),
        KTModalCreateProject.getStepper()
          .querySelector('[data-kt-element="complete-start"]')
          .addEventListener('click', function () {
            e.goTo(1);
          });
    },
  };
})();
'undefined' != typeof module &&
  void 0 !== module.exports &&
  (window.KTModalCreateProjectComplete = module.exports = KTModalCreateProjectComplete);
