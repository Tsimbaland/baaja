import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './bullets-is-body.reducer';
import { IBulletsIsBody } from 'app/shared/model/bullets-is-body.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBulletsIsBodyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BulletsIsBodyDetail = (props: IBulletsIsBodyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bulletsIsBodyEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          BulletsIsBody [<b>{bulletsIsBodyEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="isMultiSelect">Is Multi Select</span>
          </dt>
          <dd>{bulletsIsBodyEntity.isMultiSelect ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/bullets-is-body" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bullets-is-body/${bulletsIsBodyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ bulletsIsBody }: IRootState) => ({
  bulletsIsBodyEntity: bulletsIsBody.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BulletsIsBodyDetail);
