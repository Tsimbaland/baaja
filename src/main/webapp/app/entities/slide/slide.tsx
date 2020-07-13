import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './slide.reducer';
import { ISlide } from 'app/shared/model/slide.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISlideProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Slide = (props: ISlideProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { slideList, match } = props;
  return (
    <div>
      <h2 id="slide-heading">
        Slides
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Slide
        </Link>
      </h2>
      <div className="table-responsive">
        {slideList && slideList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Order</th>
                <th>Name</th>
                <th>Type</th>
                <th>Body</th>
                <th>Presentation</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {slideList.map((slide, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${slide.id}`} color="link" size="sm">
                      {slide.id}
                    </Button>
                  </td>
                  <td>{slide.order}</td>
                  <td>{slide.name}</td>
                  <td>{slide.type}</td>
                  <td>{slide.bodyId ? <Link to={`body/${slide.bodyId}`}>{slide.bodyId}</Link> : ''}</td>
                  <td>{slide.presentationId ? <Link to={`presentation/${slide.presentationId}`}>{slide.presentationId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${slide.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${slide.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${slide.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          <div className="alert alert-warning">No Slides found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ slide }: IRootState) => ({
  slideList: slide.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Slide);
