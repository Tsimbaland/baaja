import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './heading-is-body.reducer';
import { IHeadingIsBody } from 'app/shared/model/heading-is-body.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHeadingIsBodyProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const HeadingIsBody = (props: IHeadingIsBodyProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { headingIsBodyList, match } = props;
  return (
    <div>
      <h2 id="heading-is-body-heading">
        Heading Is Bodies
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Heading Is Body
        </Link>
      </h2>
      <div className="table-responsive">
        {headingIsBodyList && headingIsBodyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {headingIsBodyList.map((headingIsBody, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${headingIsBody.id}`} color="link" size="sm">
                      {headingIsBody.id}
                    </Button>
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${headingIsBody.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${headingIsBody.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${headingIsBody.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          <div className="alert alert-warning">No Heading Is Bodies found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ headingIsBody }: IRootState) => ({
  headingIsBodyList: headingIsBody.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HeadingIsBody);
