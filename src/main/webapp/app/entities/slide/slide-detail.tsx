import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './slide.reducer';
import { ISlide } from 'app/shared/model/slide.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISlideDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SlideDetail = (props: ISlideDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { slideEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Slide [<b>{slideEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="order">Order</span>
          </dt>
          <dd>{slideEntity.order}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{slideEntity.name}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{slideEntity.type}</dd>
          <dt>Body</dt>
          <dd>{slideEntity.bodyId ? slideEntity.bodyId : ''}</dd>
          <dt>Presentation</dt>
          <dd>{slideEntity.presentationId ? slideEntity.presentationId : ''}</dd>
        </dl>
        <Button tag={Link} to="/slide" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/slide/${slideEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ slide }: IRootState) => ({
  slideEntity: slide.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SlideDetail);
