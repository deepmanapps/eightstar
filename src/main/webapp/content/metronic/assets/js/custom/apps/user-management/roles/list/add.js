'use strict';
var KTUsersAddRole = (function () {
  const t = document.getElementById('kt_modal_add_role'),
    e = t.querySelector('#kt_modal_add_role_form'),
    n = new bootstrap.Modal(t);
  return {
    init: function () {
      (() => {
        var o = FormValidation.formValidation(e, {
          fields: { role_name: { validators: { notEmpty: { message: 'Role name is required' } } } },
          plugins: {
            trigger: new FormValidation.plugins.Trigger(),
            bootstrap: new FormValidation.plugins.Bootstrap5({ rowSelector: '.fv-row', eleInvalidClass: '', eleValidClass: '' }),
          },
        });
        t.querySelector('[data-kt-roles-modal-action="close"]').addEventListener('click', t => {
          t.preventDefault(),
            Swal.fire({
              text: 'Are you sure you would like to close?',
              icon: 'warning',
              showCancelButton: !0,
              buttonsStyling: !1,
              confirmButtonText: 'Yes, close it!',
              cancelButtonText: 'No, return',
              customClass: { confirmButton: 'btn btn-primary', cancelButton: 'btn btn-active-light' },
            }).then(function (t) {
              t.value && n.hide();
            });
        }),
          t.querySelector('[data-kt-roles-modal-action="cancel"]').addEventListener('click', t => {
            t.preventDefault(),
              Swal.fire({
                text: 'Are you sure you would like to cancel?',
                icon: 'warning',
                showCancelButton: !0,
                buttonsStyling: !1,
                confirmButtonText: 'Yes, cancel it!',
                cancelButtonText: 'No, return',
                customClass: { confirmButton: 'btn btn-primary', cancelButton: 'btn btn-active-light' },
              }).then(function (t) {
                t.value
                  ? (e.reset(), n.hide())
                  : 'cancel' === t.dismiss &&
                    Swal.fire({
                      text: 'Your form has not been cancelled!.',
                      icon: 'error',
                      buttonsStyling: !1,
                      confirmButtonText: 'Ok, got it!',
                      customClass: { confirmButton: 'btn btn-primary' },
                    });
              });
          });
        const r = t.querySelector('[data-kt-roles-modal-action="submit"]');
        r.addEventListener('click', function (t) {
          t.preventDefault(),
            o &&
              o.validate().then(function (t) {
                console.log('validated!'),
                  'Valid' == t
                    ? (r.setAttribute('data-kt-indicator', 'on'),
                      (r.disabled = !0),
                      setTimeout(function () {
                        r.removeAttribute('data-kt-indicator'),
                          (r.disabled = !1),
                          Swal.fire({
                            text: 'Form has been successfully submitted!',
                            icon: 'success',
                            buttonsStyling: !1,
                            confirmButtonText: 'Ok, got it!',
                            customClass: { confirmButton: 'btn btn-primary' },
                          }).then(function (t) {
                            t.isConfirmed && n.hide();
                          });
                      }, 2e3))
                    : Swal.fire({
                        text: 'Sorry, looks like there are some errors detected, please try again.',
                        icon: 'error',
                        buttonsStyling: !1,
                        confirmButtonText: 'Ok, got it!',
                        customClass: { confirmButton: 'btn btn-primary' },
                      });
              });
        });
      })(),
        (() => {
          const t = e.querySelector('#kt_roles_select_all'),
            n = e.querySelectorAll('[type="checkbox"]');
          t.addEventListener('change', t => {
            n.forEach(e => {
              e.checked = t.target.checked;
            });
          });
        })();
    },
  };
})();
KTUtil.onDOMContentLoaded(function () {
  KTUsersAddRole.init();
});
