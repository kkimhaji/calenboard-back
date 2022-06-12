package jejunu.portal.calenboard.entity;

import jejunu.portal.calenboard.model.CommonResult;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResult<T> extends CommonResult {
    private List<T> list;

}