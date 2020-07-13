import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Presentation from './presentation';
import PresentationDetail from './presentation-detail';
import PresentationUpdate from './presentation-update';
import PresentationDeleteDialog from './presentation-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PresentationDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PresentationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PresentationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PresentationDetail} />
      <ErrorBoundaryRoute path={match.url} component={Presentation} />
    </Switch>
  </>
);

export default Routes;
