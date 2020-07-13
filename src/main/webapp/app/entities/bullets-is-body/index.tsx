import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BulletsIsBody from './bullets-is-body';
import BulletsIsBodyDetail from './bullets-is-body-detail';
import BulletsIsBodyUpdate from './bullets-is-body-update';
import BulletsIsBodyDeleteDialog from './bullets-is-body-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BulletsIsBodyDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BulletsIsBodyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BulletsIsBodyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BulletsIsBodyDetail} />
      <ErrorBoundaryRoute path={match.url} component={BulletsIsBody} />
    </Switch>
  </>
);

export default Routes;
