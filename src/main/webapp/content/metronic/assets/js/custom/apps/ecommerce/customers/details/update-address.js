'use strict';
var KTModalUpdateAddress = (function () {
  var t, e, n, o, r, i, a;
  return {
    init: function () {
      (t = document.querySelector('#kt_modal_update_address')),
        (i = new bootstrap.Modal(t)),
        (r = t.querySelector('#kt_modal_update_address_form')),
        (e = r.querySelector('#kt_modal_update_address_submit')),
        (n = r.querySelector('#kt_modal_update_address_cancel')),
        (o = t.querySelector('#kt_modal_update_address_close')),
        (a = FormValidation.formValidation(r, {
          fields: {
            name: { validators: { notEmpty: { message: 'Address name is required' } } },
            country: { validators: { notEmpty: { message: 'Country is required' } } },
            address1: { validators: { notEmpty: { message: 'Address 1 is required' } } },
            city: { validators: { notEmpty: { message: 'City is required' } } },
            state: { validators: { notEmpty: { message: 'State is required' } } },
            postcode: { validators: { notEmpty: { message: 'Postcode is required' } } },
          },
          plugins: {
            trigger: new FormValidation.plugins.Trigger(),
            bootstrap: new FormValidation.plugins.Bootstrap5({ rowSelector: '.fv-row', eleInvalidClass: '', eleValidClass: '' }),
          },
        })),
        $(r.querySelector('[name="country"]')).on('change', function () {
          a.revalidateField('country');
        }),
        e.addEventListener('click', function (t) {
          t.preventDefault(),
            a &&
              a.validate().then(function (t) {
                console.log('validated!'),
                  'Valid' == t
                    ? (e.setAttribute('data-kt-indicator', 'on'),
                      (e.disabled = !0),
                      setTimeout(function () {
                        e.removeAttribute('data-kt-indicator'),
                          Swal.fire({
                            text: 'Form has been successfully submitted!',
                            icon: 'success',
                            buttonsStyling: !1,
                            confirmButtonText: 'Ok, got it!',
                            customClass: { confirmButton: 'btn btn-primary' },
                          }).then(function (t) {
                            t.isConfirmed && (i.hide(), (e.disabled = !1));
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
        }),
        n.addEventListener('click', function (t) {
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
                ? (r.reset(), i.hide())
                : 'cancel' === t.dismiss &&
                  Swal.fire({
                    text: 'Your form has not been cancelled!.',
                    icon: 'error',
                    buttonsStyling: !1,
                    confirmButtonText: 'Ok, got it!',
                    customClass: { confirmButton: 'btn btn-primary' },
                  });
            });
        }),
        o.addEventListener('click', function (t) {
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
                ? (r.reset(), i.hide())
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
    },
  };
})();
KTUtil.onDOMContentLoaded(function () {
  KTModalUpdateAddress.init();
});
