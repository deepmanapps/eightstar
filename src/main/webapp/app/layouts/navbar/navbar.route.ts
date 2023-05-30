import { Route } from '@angular/router';

import { NavbarJhComponent } from './navbar.component';

export const navbarRoute: Route = {
  path: '',
  component: NavbarJhComponent,
  outlet: 'navbar',
};
