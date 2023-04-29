import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'hotel',
        data: { pageTitle: 'eightStarApp.hotel.home.title' },
        loadChildren: () => import('./hotel/hotel.module').then(m => m.HotelModule),
      },
      {
        path: 'location',
        data: { pageTitle: 'eightStarApp.location.home.title' },
        loadChildren: () => import('./location/location.module').then(m => m.LocationModule),
      },
      {
        path: 'check-in',
        data: { pageTitle: 'eightStarApp.checkIn.home.title' },
        loadChildren: () => import('./check-in/check-in.module').then(m => m.CheckInModule),
      },
      {
        path: 'services',
        data: { pageTitle: 'eightStarApp.services.home.title' },
        loadChildren: () => import('./services/services.module').then(m => m.ServicesModule),
      },
      {
        path: 'service-request',
        data: { pageTitle: 'eightStarApp.serviceRequest.home.title' },
        loadChildren: () => import('./service-request/service-request.module').then(m => m.ServiceRequestModule),
      },
      {
        path: 'product-request',
        data: { pageTitle: 'eightStarApp.productRequest.home.title' },
        loadChildren: () => import('./product-request/product-request.module').then(m => m.ProductRequestModule),
      },
      {
        path: 'parking-all',
        data: { pageTitle: 'eightStarApp.parkingAll.home.title' },
        loadChildren: () => import('./parking-all/parking-all.module').then(m => m.ParkingAllModule),
      },
      {
        path: 'delivery-request-place',
        data: { pageTitle: 'eightStarApp.deliveryRequestPlace.home.title' },
        loadChildren: () => import('./delivery-request-place/delivery-request-place.module').then(m => m.DeliveryRequestPlaceModule),
      },
      {
        path: 'customer',
        data: { pageTitle: 'eightStarApp.customer.home.title' },
        loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule),
      },
      {
        path: 'check-out',
        data: { pageTitle: 'eightStarApp.checkOut.home.title' },
        loadChildren: () => import('./check-out/check-out.module').then(m => m.CheckOutModule),
      },
      {
        path: 'hotel-services',
        data: { pageTitle: 'eightStarApp.hotelServices.home.title' },
        loadChildren: () => import('./hotel-services/hotel-services.module').then(m => m.HotelServicesModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
